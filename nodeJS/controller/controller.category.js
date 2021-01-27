var Category = require("../model/model.goal.js")

exports.insertCategoryData = function(req,res){
    var category = new Category();
    
    category.title = req.body.title;

    category.save()
    .then(function(data){
        console.log("Category inserted")
        console.log(data)
        res.send({message: "Category inserted"})
    })
    .catch(function(err){
        console.log("Category insertion failed")
        res.send({message: "Category insertion failed"})
    })
}

exports.getCategoryData = function(req,res){


    Category.find()
    .then(function(data){
        console.log("Category found")
        console.log(data)
        res.send(data)
    })
    .catch(function(err){
        console.log("Error found")
        res.send({message: "Error found"})
    })
}