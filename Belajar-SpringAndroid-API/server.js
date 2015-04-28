/**
 * Created by rizki on 28/04/15.
 */

//dependency Node JS
var express = require('express');
var mongoose = require('mongoose');
var bodyParser = require('body-parser');
var methodOverride = require('method-override');
var morgan = require('morgan');

//config Node JS
var app = express();
app.use(express.static(__dirname + '/public'));
app.use(bodyParser.json());
app.use(methodOverride());
app.use(morgan('dev'));

routes = require('./controllers/MahasiswaController')(app);

app.all('/*', function(req, res, next) {
    res.header('Access-Control-Allow-Origin', req.headers.origin || '*');
    res.header('Access-Control-Allow-Headers', 'X-Requested-With, Content-Type');
    res.header('Access-Control-Allow-Methods', 'GET');
    next();
});

//config MongoDB
mongoose.connect('mongodb://localhost/dbMahasiswa', function (err, res) {
    if (err) {
        console.log('Koneksi MongoDB gagal');
    } else {
        console.log('Koneksi MongoDB berhasil');
    }
});

var port = 8080;
app.listen(port);
console.log('Server Running Di Port ' + port);