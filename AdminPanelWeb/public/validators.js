
        
function validPrice(price){
    if(isNaN(price) || price<0){
        return false;
    }
    else{
        return true;
    }
}

function validImage(imageFileName){
    var jpgFormat = /.jpg$/;
    var pngFormat = /.png$/;
    if(jpgFormat.test(imageFileName) || pngFormat.test(imageFileName))
    {
        return true;
    }
    else{
        return false;
    }
}

function valid3DModel(modelFileName){
    var glbFormat = /.glb$/;
    if(glbFormat.test(modelFileName))
    {
        return true;
    }
    else{
        return false;
    }
}


