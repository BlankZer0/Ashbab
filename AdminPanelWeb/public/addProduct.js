var config = {
    apiKey: "AIzaSyArvSrF9juQV5s6kSiAbgQgj9s0X0PMRTk",
    authDomain: "ashbab-db.firebaseapp.com",
    databaseURL: "https://ashbab-db.firebaseio.com",
    projectId: "ashbab-db",
    storageBucket: "ashbab-db.appspot.com",
    messagingSenderId: "877920164955"
};

firebase.initializeApp(config);

//Reference to database
const storage = firebase.storage().ref();
//Global variables
var imageUrl;
var modelUrl;
const IMAGE_UPLOADER = document.getElementById('imageUploader');
const MODEL_UPLOADER = document.getElementById('modelUploader');
const IMAGE_FILE_BUTTON = document.getElementById('imageFile');
const MODEL_FILE_BUTTON = document.getElementById('modelFile');
const SELECT_CATEGORIES = document.getElementById('category');

//The drag down menu will be populated from Firebase Categories Node
firebase.database().ref("Categories").on('value', (data)=>{
    data.forEach(element => {
        addOption(SELECT_CATEGORIES, element.val(), element.val());

        //Allowing submit only after drag down menu is set
        document.getElementById('contactForm').addEventListener('submit', submitForm);

    });
}, (err)=> {
    console.log('Error getting categories from database!' + err);
});



IMAGE_FILE_BUTTON.addEventListener('change', function(e){
    var selectedImageFile = e.target.files[0];
    var imageFileName = selectedImageFile.name;

    if(!validImage(imageFileName)){
        alert(imageFileName + " Image must be in jpg or png format!");
    }
    else{
        var imageFileName = selectedImageFile.name + Date.now();
        var imageStorageRef = firebase.storage().ref("Product-2D-Images/" + imageFileName);
        var imageUploadTask = imageStorageRef.put(selectedImageFile);
        
        imageUploadTask.on('state_changed', 
        function progress(snapshot){
            var percentage = (snapshot.bytesTransferred / snapshot.totalBytes) * 100;
            IMAGE_UPLOADER.value = percentage;
        },
        function error(err){
            console.log("Error uploading the Product Image");
        },
        function complete() {
            storage.child('Product-2D-Images/' + imageFileName).getDownloadURL().then(function(url) {
                imageUrl = url;
              }).catch(function(error) {
                // Handle any errors
              });
        });
    }

    
});

MODEL_FILE_BUTTON.addEventListener('change', function(e){
    var selectedModelFile = e.target.files[0];
    var modelFileName = selectedModelFile.name;

    if(!valid3DModel(modelFileName)){
        alert(modelFileName + " Model must be in glb format!");
    }
    else{
        var modelFileName = selectedModelFile.name + Date.now();
        var modelStorageRef = firebase.storage().ref("Product-3D-Models/" + modelFileName );
        var modelUploadTask = modelStorageRef.put(selectedModelFile);
        modelUploadTask.on('state_changed', 
        function progress(snapshot){
            var percentage = (snapshot.bytesTransferred / snapshot.totalBytes) * 100;
            MODEL_UPLOADER.value = percentage;
        },
        function error(err){
            console.log("Error uploading the Product Model");
        },
        function complete() {
            storage.child('Product-3D-Models/' + modelFileName).getDownloadURL().then(function(url) {
                modelUrl = url;
              }).catch(function(error) {
                // Handle any errors
              });
        });
    }
    
});


function submitForm(e){
    e.preventDefault();
    
    var id = getInputval("id");
    var name = getInputval("name");
    var category = getInputval("category");
    var description = getInputval("description");
    var image = imageUrl;
    var model = modelUrl;
    var price = parseFloat(getInputval("price"));

    if(!validPrice(price)){
        alert("Not a valid price");
    }
    else{
        saveProduct(id, name, category, description, image, model, price);
        alert("Product updated");
    }
}



addOption = function(selectbox, text, value) {
    var optn = document.createElement("OPTION");
    optn.text = text;
    optn.value = value;
    selectbox.options.add(optn);  
}

function getInputval(id){
    return document.getElementById(id).value;
}

function saveProduct(id, name, category, description, image, model, price){
    firebase.database().ref().child('Products').push().set({
        productID: id,
        productName: name,
        category: category,
        description: description,
        imageUrl: image,
        model3dUrl: model,
        productPrice: price
    })
}
