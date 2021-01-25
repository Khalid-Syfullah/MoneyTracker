var Wallet = require("../model/model.wallet.js")

exports.insertWalletData = function(req,res){
    var wallet = new Wallet();
    
    wallet.title = req.body.title;
    wallet.type = req.body.type;
    wallet.currency = req.body.currency;
    wallet.balance = "20";

    wallet.save()
    .then(function(data){
        console.log("Wallet inserted")
        console.log(data)
        res.send({message: "Wallet inserted"})
    })
    .catch(function(err){
        console.log("Wallet insertion failed")
        res.send({message: "Wallet insertion failed"})
    })
}