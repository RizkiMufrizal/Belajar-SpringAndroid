/**
 * Created by rizki on 28/04/15.
 */

var Mahasiswa = require('../models/Mahasiswa');

module.exports = function (app) {

    findAllMahasiswas = function (req, res) {
        return Mahasiswa.find(function (err, mahasiswas) {
            if (err) return res.send(err);

            res.send(mahasiswas);
        });
    };

    findByIdMahasiswa = function (req, res) {
        return Mahasiswa.find({npm: req.params.npm}, function (err, mahasiswa) {
            if (!mahasiswa) return res.send('404 Not Found');

            if (err) return res.send(err);

            return res.send(mahasiswa);
        });
    };

    addMahasiswa = function (req, res) {
        var mahasiswa = new Mahasiswa({
            npm: req.body.npm,
            nama: req.body.nama,
            kelas: req.body.kelas
        });

        mahasiswa.save(function (err) {
            if (err) return res.send(err);

            return res.send('Data Tersimpan');
        });
    };

    updateMahasiswa = function (req, res) {
        return Mahasiswa.update({npm: req.params.npm}, req.body, function (err) {
            if (err) return res.send(err);

            return res.send('Data Berhasil Di Update');
        });
    };

    deleteMahasiswa = function (req, res) {
        return Mahasiswa.remove({npm: req.params.npm}, function (err) {
            if (err) return res.send(err);

            return res.send('Data Berhasil dihapus');
        });
    };

    app.get('/mahasiswa', findAllMahasiswas);
    app.get('/mahasiswa/:npm', findByIdMahasiswa);
    app.post('/mahasiswa', addMahasiswa);
    app.put('/mahasiswa/:npm', updateMahasiswa);
    app.delete('/mahasiswa/:npm', deleteMahasiswa);

};