module.exports = (app) => {
    var user = require("../controller/controller.user.js")

    app.get('/',user.create)
    app.get('/api/getter',user.find)
    app.get('/api/delete',user.delete)
}