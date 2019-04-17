/*
 This class will model the Product
 The image and model attribute will
 only hold the url of their download links
*/

class product{
    constructor(id, name, category, description, image, model, price){
        this.id = id;
        this.name = name;
        this.category = category;
        this.description = description;
        this.image = image;
        this.model = model;
        this.price = price;
        this.dimension = dimension;
    }
}