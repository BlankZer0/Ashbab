package com.ashbab.ashbabapp.ui.productPage;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.widget.Toast;

import com.ashbab.ashbabapp.R;
import com.google.ar.core.Anchor;
import com.google.ar.core.HitResult;
import com.google.ar.core.Plane;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.assets.RenderableSource;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.ux.ArFragment;
import com.google.ar.sceneform.ux.TransformableNode;

import java.util.Objects;

/**
 * This class is responsible for handling the Camera view that shows the Augmented Reality Model
 */
public class ArCameraActivity extends AppCompatActivity
{
    private static final String LOG_TAG = ArCameraActivity.class.getSimpleName();
    private static final double MIN_OPENGL_VERSION = 3.0;

    private ArFragment arFragment;
    private ModelRenderable modelRenderable;

    private static final String URL_KEY = "url_key";  // key to get the url string

    @Override
    @SuppressWarnings("FutureReturnValueIgnored")
    // CompletableFuture requires api level 24
    // FutureReturnValueIgnored is not valid
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        Log.v(LOG_TAG, LOG_TAG + " Created");

        // Check whether or not the device supports AR
        if (!checkIsSupportedDeviceOrFinish(this))
        {
            return;
        }

        setContentView(R.layout.activity_ar_camera);
        arFragment = (ArFragment) getSupportFragmentManager().findFragmentById(R.id.ux_fragment);

        Log.v(LOG_TAG, LOG_TAG + " Created");

        // Link of the 3D Object
        String asset = Objects.requireNonNull(getIntent().getExtras()).getString(URL_KEY);
        Log.v(LOG_TAG, "Asset Link: " + asset);

        // When you build a Renderable, Sceneform loads its resources in the background
        // while returning a CompletableFuture, means the object is being built on a separate thread
        ModelRenderable.builder()
                .setSource(this, RenderableSource.builder().setSource(
                        this, Uri.parse(asset), RenderableSource.SourceType.GLB)
                        .setScale(0.25f)  // Scale the original image to 25%
                        .setRecenterMode(RenderableSource.RecenterMode.ROOT)
                        .build())
                .setRegistryId(asset)
                .build()
                .thenAccept(renderable -> modelRenderable = renderable)
                .exceptionally(
                        throwable -> {
                            Toast toast =
                                    Toast.makeText(this, "Unable to load renderable", Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                            return null;
                        });

        // Anchors the 3D object on the detected plain
        arFragment.setOnTapArPlaneListener(
                (HitResult hitResult, Plane plane, MotionEvent motionEvent) -> {
                    if (modelRenderable == null) {
                        return;
                    }

                    // Create the Anchor.
                    Anchor anchor = hitResult.createAnchor();
                    AnchorNode anchorNode = new AnchorNode(anchor);
                    anchorNode.setParent(arFragment.getArSceneView().getScene());

                    // Create the transformable model and add it to the anchor.
                    TransformableNode transformableNode = new TransformableNode(arFragment.getTransformationSystem());
                    transformableNode.setParent(anchorNode);
                    transformableNode.setRenderable(modelRenderable);
                    transformableNode.select();
                });
    }

    /**
     * <p>Sceneform requires Android N on the device as well as OpenGL 3.0 capabilities.
     * <p>Finishes the activity if Sceneform can not run
     */
    public static boolean checkIsSupportedDeviceOrFinish(final Activity activity)
    {
        // This checks for the android version of the device the app is running on
        // Uncomment this if the minimum required android version is lower than Android 7.0
        // as augmented reality features does not support on those devices
//        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N)
//        {
//            Log.e(TAG, "Sceneform requires Android N or later");
//            Toast.makeText(activity, "Sceneform requires Android N or later", Toast.LENGTH_LONG).show();
//            activity.finish();
//            return false;
//        }

        // Checks the openGL version
        String openGlVersionString =
                ((ActivityManager) activity.getSystemService(Context.ACTIVITY_SERVICE))
                        .getDeviceConfigurationInfo()
                        .getGlEsVersion();
        if (Double.parseDouble(openGlVersionString) < MIN_OPENGL_VERSION) {
            Log.e(LOG_TAG, "Sceneform requires OpenGL ES 3.0 later");
            Toast.makeText(activity, "Sceneform requires OpenGL ES 3.0 or later", Toast.LENGTH_LONG)
                    .show();
            activity.finish();
            return false;
        }
        return true;
    }

    /**
     * By using this method other activities can create an intent to start this activity
     * @param context of the previous activity
     * @param modelUrl sent from the previous activity
     * @return the intent required for starting ProductDetailsActivity
     */
    public static Intent buildIntent(Context context, String modelUrl)
    {
        Intent intent = new Intent(context, ArCameraActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(URL_KEY, modelUrl);
        intent.putExtras(bundle);

        Log.v(LOG_TAG, "AR Camera Activity Intent built");
        return intent;
    }
}
