const config = {
    apiKey: "AIzaSyArvSrF9juQV5s6kSiAbgQgj9s0X0PMRTk",
    authDomain: "ashbab-db.firebaseapp.com",
    databaseURL: "https://ashbab-db.firebaseio.com",
    projectId: "ashbab-db",
    storageBucket: "ashbab-db.appspot.com",
    messagingSenderId: "877920164955"
};


const categoryList = document.getElementById("categoryList");

window.onload = function()
{
    document.getElementById('contactForm').addEventListener('submit', submitForm);
    firebase.initializeApp(config);

    // The list will be populated by the categories from firebase
    firebase.database().ref("Categories").on('value', (data)=>{
    
        while (categoryList.firstChild) {
            categoryList.removeChild(categoryList.firstChild);
        }
        data.forEach(element => {
    
            var categoryListItem = document.createElement("li");
            var categoryListValue = document.createTextNode(element.child('CategoryName').val());
            categoryListItem.appendChild(categoryListValue);
            categoryList.appendChild(categoryListItem);  
    
        });
    }, (err)=> {
        console.log('Error getting categories from database!' + err);
    });
}






function submitForm(e){
    e.preventDefault();
    var newCategory = document.getElementById("newCategory").value;
    console.log(newCategory);
    firebase.database().ref().child('Categories').push().set({
        CategoryName: newCategory
    });
    
}