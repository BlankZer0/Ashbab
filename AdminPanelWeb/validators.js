
        
function validPrice(price){
    if(isNaN(price) || price<0){
        return false;
    }
    else{
        return true;
    }
}

function validImage(imageFileName){
    var re = /.jpg$/;
    if(re.test(imageFileName))
    {
        return true;
    }
    else{
        return false;
    }
}

function valid3DModel(modelFileName){
    var re = /.glb$/;
    if(re.test(modelFileName))
    {
        return true;
    }
    else{
        return false;
    }
}


