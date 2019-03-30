
// Initialize Firebase
var config = {
    apiKey: "AIzaSyArvSrF9juQV5s6kSiAbgQgj9s0X0PMRTk",
    authDomain: "ashbab-db.firebaseapp.com",
    databaseURL: "https://ashbab-db.firebaseio.com",
    projectId: "ashbab-db",
    storageBucket: "ashbab-db.appspot.com",
    messagingSenderId: "877920164955"
};
firebase.initializeApp(config);


//Global variables
var selectedImageFile;
var selectedModelFile;
var imageStorageRef;
var modelStorageRef;
var imageFileName;
var modelFileName;
var imageUploader = document.getElementById('imageUploader');
var modelUploader = document.getElementById('modelUploader');
var imageFileButton = document.getElementById('imageFile');
var modelFileButton = document.getElementById('modelFile');


imageFileButton.addEventListener('change', function(e){
    selectedImageFile = e.target.files[0];
    imageFileName = selectedImageFile.name + Date.now();
    imageStorageRef = firebase.storage().ref("Product-2D-Images/" + imageFileName);
});
modelFileButton.addEventListener('change', function(e){
    selectedModelFile = e.target.files[0];
    modelFileName = selectedModelFile.name + Date.now();
    modelStorageRef = firebase.storage().ref("Product-3D-Models/" + modelFileName );
});


//Reference to database
var productsRef = firebase.database().ref('Products');

//Main
document.getElementById('contactForm').addEventListener('submit', submitForm);

function submitForm(e){
    e.preventDefault();
    
    var id = getInputval("id");
    var name = getInputval("name");
    var category = getInputval("category");
    var description = getInputval("description");
    var image = imageFileName;
    var model = modelFileName;
    var price = getInputval("price");

    uploadFilesToStorage();
    saveProduct(id, name, category, description, image, model, price);

}

function getInputval(id){
    return document.getElementById(id).value;
}

function saveProduct(id, name, category, description, image, model, price){
    var newProductsRef = productsRef.push();
    newProductsRef.set({
        productID: id,
        productName: name,
        category: category,
        description: description,
        imageUrl: image,
        model3dUrl: model,
        productPrice: price
    })
}
function uploadFilesToStorage(){
    var imageUploadTask = imageStorageRef.put(selectedImageFile);
    imageUploadTask.on('state_changed', 
        function progress(snapshot){
            var percentage = (snapshot.bytesTransferred / snapshot.totalBytes) * 100;
            imageUploader.value = percentage;
        },
        function error(err){
            console.log("Error uploading the Product Image");
        },
        function complete() {

        });

    var modelUploadTask = modelStorageRef.put(selectedModelFile);
    modelUploadTask.on('state_changed', 
        function progress(snapshot){
            var percentage = (snapshot.bytesTransferred / snapshot.totalBytes) * 100;
            modelUploader.value = percentage;
        },
        function error(err){
            console.log("Error uploading the Product Model");
        },
        function complete() {

        });
}

