/**
 * Created by rizki on 28/04/15.
 */


var mongoose = require('mongoose');
var Schema = mongoose.Schema;

var Mahasiswa = new Schema(
    {
        npm: {
            type: String,
            require: true
        },
        nama: {
            type: String,
            require: true
        },
        kelas: {
            type: String,
            require: true
        }
    }
);

//validasi
Mahasiswa.path('npm').validate(function (v) {
    return ((v != '') && (v != null));
});

module.exports = mongoose.model('Mahasiswa', Mahasiswa);