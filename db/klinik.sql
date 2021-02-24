-- phpMyAdmin SQL Dump
-- version 5.0.3
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Feb 24, 2021 at 01:58 PM
-- Server version: 10.4.14-MariaDB
-- PHP Version: 7.2.34

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `klinik`
--

-- --------------------------------------------------------

--
-- Table structure for table `bayar_layanan`
--

CREATE TABLE `bayar_layanan` (
  `id_bayar_layanan` varchar(20) NOT NULL,
  `id_layanan` varchar(20) NOT NULL,
  `id_pasien` varchar(20) NOT NULL,
  `tgl_layanan` date NOT NULL,
  `keterangan` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `bayar_layanan`
--

INSERT INTO `bayar_layanan` (`id_bayar_layanan`, `id_layanan`, `id_pasien`, `tgl_layanan`, `keterangan`) VALUES
('BL001', 'LP001', 'P001', '2021-02-20', 'BPJS Lunas');

-- --------------------------------------------------------

--
-- Table structure for table `bayar_obat`
--

CREATE TABLE `bayar_obat` (
  `id_bayar_obat` varchar(20) NOT NULL,
  `id_obat` varchar(20) NOT NULL,
  `id_pasien` varchar(20) NOT NULL,
  `id_resep` varchar(20) NOT NULL,
  `jumlah` int(11) NOT NULL,
  `jenis_pembayaran` enum('cash','bpjs','bank') NOT NULL,
  `waktu` timestamp NOT NULL DEFAULT current_timestamp(),
  `id_user` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `bayar_obat`
--

INSERT INTO `bayar_obat` (`id_bayar_obat`, `id_obat`, `id_pasien`, `id_resep`, `jumlah`, `jenis_pembayaran`, `waktu`, `id_user`) VALUES
('BO001', 'OU001', 'P001', 'RO001', 12, 'bpjs', '2021-02-20 03:02:11', 'UP001');

-- --------------------------------------------------------

--
-- Table structure for table `dokter`
--

CREATE TABLE `dokter` (
  `id_dokter` varchar(20) NOT NULL,
  `nama_dokter` varchar(200) NOT NULL,
  `tgl_lahir` date NOT NULL,
  `id_poli` varchar(20) NOT NULL,
  `jenis_kelamin` enum('L','P') NOT NULL,
  `alamat` varchar(100) NOT NULL,
  `no_hp` varchar(20) NOT NULL,
  `npwp` varchar(20) NOT NULL,
  `no_ktp` varchar(20) NOT NULL,
  `email` varchar(100) NOT NULL,
  `password` varchar(25) NOT NULL,
  `waktu` timestamp NOT NULL DEFAULT current_timestamp(),
  `id_user` varchar(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `dokter`
--

INSERT INTO `dokter` (`id_dokter`, `nama_dokter`, `tgl_lahir`, `id_poli`, `jenis_kelamin`, `alamat`, `no_hp`, `npwp`, `no_ktp`, `email`, `password`, `waktu`, `id_user`) VALUES
('DA001', 'dr. M. Solichin Basrie, Sp.A', '1965-10-21', 'PA001', 'L', 'Jl. Garuda Raya No. 28', '081237465567', '092452256707000', '3118085739384484', 'solichin.basrie@gmail.com', 'solichin123', '2021-02-23 13:02:25', 'UD002'),
('DU001', 'dr. Ishak Husein', '1973-05-21', 'PU001', 'L', 'Komplek Timah Blok DD.5 No.3', '083455767783', '092452943407000', '3128082839384484', 'contact.ishakhusein@gmail.com', 'ishakhusein', '2021-02-13 07:21:19', 'UD001');

-- --------------------------------------------------------

--
-- Table structure for table `kamar`
--

CREATE TABLE `kamar` (
  `id_kamar` varchar(20) NOT NULL,
  `nama_ruang` varchar(100) NOT NULL,
  `no_ruang` varchar(10) NOT NULL,
  `kelas` enum('I','II','III','VIP','isolasi') NOT NULL,
  `des_kamar` varchar(100) NOT NULL,
  `kapasitas` int(11) NOT NULL,
  `terisi` enum('y','t') NOT NULL,
  `status` enum('ok','full','maintenance') NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `kamar`
--

INSERT INTO `kamar` (`id_kamar`, `nama_ruang`, `no_ruang`, `kelas`, `des_kamar`, `kapasitas`, `terisi`, `status`) VALUES
('KM001', 'Mawar', '01', 'I', 'Korban kecelakaan', 6, 'y', 'ok');

-- --------------------------------------------------------

--
-- Table structure for table `karyawan`
--

CREATE TABLE `karyawan` (
  `id_karyawan` varchar(20) NOT NULL,
  `nama_karyawan` varchar(200) NOT NULL,
  `tgl_lahir` date NOT NULL,
  `bidang_pekerjaan` enum('administrasi','suster','rm','apoteker') NOT NULL,
  `jenis_kelamin` enum('L','P') NOT NULL,
  `alamat` varchar(100) NOT NULL,
  `no_hp` varchar(20) NOT NULL,
  `no_ktp` varchar(20) NOT NULL,
  `email` varchar(50) NOT NULL,
  `npwp` varchar(20) NOT NULL,
  `waktu` timestamp NOT NULL DEFAULT current_timestamp(),
  `id_user` varchar(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `karyawan`
--

INSERT INTO `karyawan` (`id_karyawan`, `nama_karyawan`, `tgl_lahir`, `bidang_pekerjaan`, `jenis_kelamin`, `alamat`, `no_hp`, `no_ktp`, `email`, `npwp`, `waktu`, `id_user`) VALUES
('K0001', 'Bayu Setiawan', '0000-00-00', '', '', 'Jl. Turut Berbudaya', '085688928383', '', '', '', '2021-02-23 12:47:14', NULL);

-- --------------------------------------------------------

--
-- Table structure for table `layanan`
--

CREATE TABLE `layanan` (
  `id_layanan` varchar(20) NOT NULL,
  `des_layanan` varchar(100) NOT NULL,
  `biaya_layanan` int(11) NOT NULL,
  `keterangan` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `layanan`
--

INSERT INTO `layanan` (`id_layanan`, `des_layanan`, `biaya_layanan`, `keterangan`) VALUES
('LP001', 'Poliklinik Umum', 50000, 'dr. Ishak Husein');

-- --------------------------------------------------------

--
-- Table structure for table `obat`
--

CREATE TABLE `obat` (
  `id_obat` varchar(20) NOT NULL,
  `nama_obat` varchar(200) NOT NULL,
  `satuan` enum('botol','kapsul','butir') NOT NULL,
  `stok` int(11) NOT NULL,
  `harga_jual` int(11) NOT NULL,
  `no_faktur` varchar(20) NOT NULL,
  `id_user` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `obat`
--

INSERT INTO `obat` (`id_obat`, `nama_obat`, `satuan`, `stok`, `harga_jual`, `no_faktur`, `id_user`) VALUES
('OU001', 'Paracetamol', 'butir', 250, 12000, '013001', 'US001');

-- --------------------------------------------------------

--
-- Table structure for table `pasien`
--

CREATE TABLE `pasien` (
  `id_pasien` varchar(20) NOT NULL,
  `nama_pasien` varchar(200) NOT NULL,
  `tgl_lahir` date NOT NULL,
  `jenis_kelamin` enum('L','P') NOT NULL,
  `no_ktp` varchar(20) NOT NULL,
  `alamat` varchar(200) NOT NULL,
  `no_hp` varchar(20) NOT NULL,
  `gol_darah` enum('A','B','AB','O') NOT NULL,
  `password` varchar(20) NOT NULL,
  `id_user` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `pasien`
--

INSERT INTO `pasien` (`id_pasien`, `nama_pasien`, `tgl_lahir`, `jenis_kelamin`, `no_ktp`, `alamat`, `no_hp`, `gol_darah`, `password`, `id_user`) VALUES
('P001', 'Athallah Rizaldi', '2003-05-11', 'L', '3175081105031002', 'Jl. Hias No. 35', '085691645955', 'O', 'atha1234', 'UP001'),
('P002', 'Haditya Fajri Bahri Ramadhan', '2002-02-22', 'L', '31750811029393330', 'Jl. Asem', '081284758899', 'AB', 'haditya123', 'UP002');

-- --------------------------------------------------------

--
-- Table structure for table `pendaftaran`
--

CREATE TABLE `pendaftaran` (
  `no_antrian` varchar(20) NOT NULL,
  `id_pasien` varchar(20) NOT NULL,
  `id_poli` varchar(20) NOT NULL,
  `tgl_daftar` date NOT NULL,
  `keterangan` varchar(100) NOT NULL,
  `waktu` timestamp NOT NULL DEFAULT current_timestamp(),
  `id_user` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `pendaftaran`
--

INSERT INTO `pendaftaran` (`no_antrian`, `id_pasien`, `id_poli`, `tgl_daftar`, `keterangan`, `waktu`, `id_user`) VALUES
('001', 'P002', 'PU001', '2020-02-21', 'Pasien Jadwal Pagi', '2020-02-20 23:29:19', 'UP002');

-- --------------------------------------------------------

--
-- Table structure for table `poli`
--

CREATE TABLE `poli` (
  `id_poli` varchar(20) NOT NULL,
  `nama_poli` varchar(100) NOT NULL,
  `id_dokter` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `poli`
--

INSERT INTO `poli` (`id_poli`, `nama_poli`, `id_dokter`) VALUES
('PA001', 'Poliklinik Anak', 'DA001'),
('PU001', 'Poliklinik Umum', 'DU001');

-- --------------------------------------------------------

--
-- Table structure for table `rawat_inap`
--

CREATE TABLE `rawat_inap` (
  `id_rawat` varchar(20) NOT NULL,
  `id_pasien` varchar(20) NOT NULL,
  `id_kamar` varchar(20) NOT NULL,
  `tgl_check_in` date NOT NULL,
  `tgl_check_out` date NOT NULL,
  `keterangan` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `rawat_inap`
--

INSERT INTO `rawat_inap` (`id_rawat`, `id_pasien`, `id_kamar`, `tgl_check_in`, `tgl_check_out`, `keterangan`) VALUES
('RA001', 'P001', 'KM001', '2021-02-19', '2021-03-05', 'Pasien Isolasi Covid-19');

-- --------------------------------------------------------

--
-- Table structure for table `resep`
--

CREATE TABLE `resep` (
  `id_resep` varchar(20) NOT NULL,
  `id_obat` varchar(20) NOT NULL,
  `jumlah` int(11) NOT NULL,
  `keterangan` varchar(100) NOT NULL,
  `waktu` timestamp NOT NULL DEFAULT current_timestamp(),
  `id_user` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `resep`
--

INSERT INTO `resep` (`id_resep`, `id_obat`, `jumlah`, `keterangan`, `waktu`, `id_user`) VALUES
('RO001', 'OU001', 12, 'Obat Umum', '2021-02-21 04:41:48', 'UP001');

-- --------------------------------------------------------

--
-- Table structure for table `rm`
--

CREATE TABLE `rm` (
  `id_pasien` varchar(20) NOT NULL,
  `tgl_daftar` date NOT NULL,
  `id_poli` varchar(20) NOT NULL,
  `id_dokter` varchar(20) NOT NULL,
  `berat` decimal(5,2) NOT NULL,
  `tinggi` decimal(5,2) NOT NULL,
  `tensi` varchar(10) NOT NULL,
  `diagnosa` varchar(200) NOT NULL,
  `id_resep` varchar(20) NOT NULL,
  `waktu` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `supplier`
--

CREATE TABLE `supplier` (
  `id_supplier` varchar(20) NOT NULL,
  `nama_supplier` varchar(100) NOT NULL,
  `alamat` varchar(200) NOT NULL,
  `no_hp` varchar(20) NOT NULL,
  `email` varchar(50) NOT NULL,
  `waktu` timestamp NOT NULL DEFAULT current_timestamp(),
  `id_user` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `supplier`
--

INSERT INTO `supplier` (`id_supplier`, `nama_supplier`, `alamat`, `no_hp`, `email`, `waktu`, `id_user`) VALUES
('SP001', 'Kimia Farma', 'Jl. Veteran No. 9', '1500255', 'webmaster@kimiafarma.co.id', '2021-02-18 15:25:29', 'UA001');

-- --------------------------------------------------------

--
-- Table structure for table `supply_obat`
--

CREATE TABLE `supply_obat` (
  `id_pembelian` varchar(20) NOT NULL,
  `id_supplier` varchar(20) NOT NULL,
  `id_obat` varchar(20) NOT NULL,
  `no_faktur` varchar(20) NOT NULL,
  `tgl_faktur` date NOT NULL,
  `harga_beli` int(11) NOT NULL,
  `jumlah` int(11) NOT NULL,
  `keterangan` varchar(100) NOT NULL,
  `tgl_expired` date NOT NULL,
  `waktu` timestamp NOT NULL DEFAULT current_timestamp(),
  `id_user` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `supply_obat`
--

INSERT INTO `supply_obat` (`id_pembelian`, `id_supplier`, `id_obat`, `no_faktur`, `tgl_faktur`, `harga_beli`, `jumlah`, `keterangan`, `tgl_expired`, `waktu`, `id_user`) VALUES
('PO001', 'SP001', 'OU001', '11001100', '2021-02-19', 2500000, 250, 'Stok bulanan', '2022-01-20', '2021-02-19 02:25:21', 'UK001');

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `id_user` varchar(20) NOT NULL,
  `username` varchar(50) NOT NULL,
  `password` varchar(20) NOT NULL,
  `nama` varchar(200) NOT NULL,
  `role` enum('admin','dokter','karyawan','pasien','supplier') NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`id_user`, `username`, `password`, `nama`, `role`) VALUES
('UA001', 'admin', 'admin123', 'Athallah Rizaldi', 'admin'),
('UD001', 'ishakdokter', 'ishakhusein', 'dr. Ishak Husein', 'dokter'),
('UD002', 'solichinbasrie', 'solichin123', 'dr. Solichin Basrie, Sp.A', 'dokter'),
('UK001', 'karyawan', 'karyawan1', 'Haditya Fajri Bahri Ramadhan', 'karyawan'),
('UP001', 'athallah', 'atha1234', 'Athallah Rizaldi', 'pasien'),
('UP002', 'hditya', 'haditya123', 'Haditya Fajri Bahri Ramadhan', 'pasien'),
('US001', 'kimiafarma', 'supplierkf', 'PT. Kimia Farma Tbk.', 'supplier');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `bayar_layanan`
--
ALTER TABLE `bayar_layanan`
  ADD PRIMARY KEY (`id_bayar_layanan`),
  ADD KEY `id_layanan` (`id_layanan`),
  ADD KEY `id_pasien` (`id_pasien`);

--
-- Indexes for table `bayar_obat`
--
ALTER TABLE `bayar_obat`
  ADD PRIMARY KEY (`id_bayar_obat`),
  ADD KEY `id_obat` (`id_obat`),
  ADD KEY `id_pasien` (`id_pasien`),
  ADD KEY `id_resep` (`id_resep`),
  ADD KEY `id_user` (`id_user`);

--
-- Indexes for table `dokter`
--
ALTER TABLE `dokter`
  ADD PRIMARY KEY (`id_dokter`),
  ADD KEY `id_user` (`id_user`);

--
-- Indexes for table `kamar`
--
ALTER TABLE `kamar`
  ADD PRIMARY KEY (`id_kamar`);

--
-- Indexes for table `karyawan`
--
ALTER TABLE `karyawan`
  ADD PRIMARY KEY (`id_karyawan`),
  ADD KEY `id_user` (`id_user`);

--
-- Indexes for table `layanan`
--
ALTER TABLE `layanan`
  ADD PRIMARY KEY (`id_layanan`);

--
-- Indexes for table `obat`
--
ALTER TABLE `obat`
  ADD PRIMARY KEY (`id_obat`),
  ADD KEY `id_user` (`id_user`);

--
-- Indexes for table `pasien`
--
ALTER TABLE `pasien`
  ADD PRIMARY KEY (`id_pasien`),
  ADD KEY `id_user` (`id_user`);

--
-- Indexes for table `pendaftaran`
--
ALTER TABLE `pendaftaran`
  ADD PRIMARY KEY (`no_antrian`),
  ADD KEY `id_pasien` (`id_pasien`),
  ADD KEY `id_poli` (`id_poli`),
  ADD KEY `id_user` (`id_user`);

--
-- Indexes for table `poli`
--
ALTER TABLE `poli`
  ADD PRIMARY KEY (`id_poli`),
  ADD KEY `id_dokter` (`id_dokter`);

--
-- Indexes for table `rawat_inap`
--
ALTER TABLE `rawat_inap`
  ADD PRIMARY KEY (`id_rawat`),
  ADD KEY `id_pasien` (`id_pasien`),
  ADD KEY `id_kamar` (`id_kamar`);

--
-- Indexes for table `resep`
--
ALTER TABLE `resep`
  ADD PRIMARY KEY (`id_resep`),
  ADD KEY `id_obat` (`id_obat`),
  ADD KEY `id_user` (`id_user`);

--
-- Indexes for table `rm`
--
ALTER TABLE `rm`
  ADD PRIMARY KEY (`id_pasien`),
  ADD KEY `id_poli` (`id_poli`),
  ADD KEY `id_dokter` (`id_dokter`),
  ADD KEY `id_resep` (`id_resep`);

--
-- Indexes for table `supplier`
--
ALTER TABLE `supplier`
  ADD PRIMARY KEY (`id_supplier`),
  ADD KEY `id_user` (`id_user`);

--
-- Indexes for table `supply_obat`
--
ALTER TABLE `supply_obat`
  ADD PRIMARY KEY (`id_pembelian`),
  ADD KEY `id_supplier` (`id_supplier`),
  ADD KEY `id_obat` (`id_obat`),
  ADD KEY `id_user` (`id_user`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id_user`);

--
-- Constraints for dumped tables
--

--
-- Constraints for table `bayar_layanan`
--
ALTER TABLE `bayar_layanan`
  ADD CONSTRAINT `bayar_layanan_ibfk_1` FOREIGN KEY (`id_layanan`) REFERENCES `layanan` (`id_layanan`),
  ADD CONSTRAINT `bayar_layanan_ibfk_2` FOREIGN KEY (`id_pasien`) REFERENCES `pasien` (`id_pasien`),
  ADD CONSTRAINT `bayar_layanan_ibfk_3` FOREIGN KEY (`id_layanan`) REFERENCES `layanan` (`id_layanan`),
  ADD CONSTRAINT `bayar_layanan_ibfk_4` FOREIGN KEY (`id_pasien`) REFERENCES `pasien` (`id_pasien`);

--
-- Constraints for table `bayar_obat`
--
ALTER TABLE `bayar_obat`
  ADD CONSTRAINT `bayar_obat_ibfk_1` FOREIGN KEY (`id_obat`) REFERENCES `obat` (`id_obat`),
  ADD CONSTRAINT `bayar_obat_ibfk_2` FOREIGN KEY (`id_pasien`) REFERENCES `pasien` (`id_pasien`),
  ADD CONSTRAINT `bayar_obat_ibfk_3` FOREIGN KEY (`id_obat`) REFERENCES `obat` (`id_obat`),
  ADD CONSTRAINT `bayar_obat_ibfk_4` FOREIGN KEY (`id_pasien`) REFERENCES `pasien` (`id_pasien`),
  ADD CONSTRAINT `bayar_obat_ibfk_5` FOREIGN KEY (`id_resep`) REFERENCES `resep` (`id_resep`),
  ADD CONSTRAINT `bayar_obat_ibfk_6` FOREIGN KEY (`id_user`) REFERENCES `user` (`id_user`);

--
-- Constraints for table `dokter`
--
ALTER TABLE `dokter`
  ADD CONSTRAINT `dokter_ibfk_1` FOREIGN KEY (`id_user`) REFERENCES `user` (`id_user`);

--
-- Constraints for table `karyawan`
--
ALTER TABLE `karyawan`
  ADD CONSTRAINT `karyawan_ibfk_1` FOREIGN KEY (`id_user`) REFERENCES `user` (`id_user`);

--
-- Constraints for table `obat`
--
ALTER TABLE `obat`
  ADD CONSTRAINT `obat_ibfk_1` FOREIGN KEY (`id_user`) REFERENCES `user` (`id_user`);

--
-- Constraints for table `pasien`
--
ALTER TABLE `pasien`
  ADD CONSTRAINT `pasien_ibfk_1` FOREIGN KEY (`id_user`) REFERENCES `user` (`id_user`);

--
-- Constraints for table `pendaftaran`
--
ALTER TABLE `pendaftaran`
  ADD CONSTRAINT `pendaftaran_ibfk_1` FOREIGN KEY (`id_pasien`) REFERENCES `pasien` (`id_pasien`),
  ADD CONSTRAINT `pendaftaran_ibfk_2` FOREIGN KEY (`id_poli`) REFERENCES `poli` (`id_poli`),
  ADD CONSTRAINT `pendaftaran_ibfk_3` FOREIGN KEY (`id_user`) REFERENCES `user` (`id_user`);

--
-- Constraints for table `poli`
--
ALTER TABLE `poli`
  ADD CONSTRAINT `poli_ibfk_1` FOREIGN KEY (`id_dokter`) REFERENCES `dokter` (`id_dokter`);

--
-- Constraints for table `rawat_inap`
--
ALTER TABLE `rawat_inap`
  ADD CONSTRAINT `rawat_inap_ibfk_1` FOREIGN KEY (`id_pasien`) REFERENCES `pasien` (`id_pasien`),
  ADD CONSTRAINT `rawat_inap_ibfk_2` FOREIGN KEY (`id_kamar`) REFERENCES `kamar` (`id_kamar`);

--
-- Constraints for table `resep`
--
ALTER TABLE `resep`
  ADD CONSTRAINT `resep_ibfk_1` FOREIGN KEY (`id_obat`) REFERENCES `obat` (`id_obat`),
  ADD CONSTRAINT `resep_ibfk_2` FOREIGN KEY (`id_user`) REFERENCES `user` (`id_user`);

--
-- Constraints for table `rm`
--
ALTER TABLE `rm`
  ADD CONSTRAINT `rm_ibfk_1` FOREIGN KEY (`id_poli`) REFERENCES `poli` (`id_poli`),
  ADD CONSTRAINT `rm_ibfk_2` FOREIGN KEY (`id_dokter`) REFERENCES `dokter` (`id_dokter`),
  ADD CONSTRAINT `rm_ibfk_3` FOREIGN KEY (`id_resep`) REFERENCES `resep` (`id_resep`);

--
-- Constraints for table `supplier`
--
ALTER TABLE `supplier`
  ADD CONSTRAINT `supplier_ibfk_1` FOREIGN KEY (`id_user`) REFERENCES `user` (`id_user`);

--
-- Constraints for table `supply_obat`
--
ALTER TABLE `supply_obat`
  ADD CONSTRAINT `supply_obat_ibfk_1` FOREIGN KEY (`id_supplier`) REFERENCES `supplier` (`id_supplier`),
  ADD CONSTRAINT `supply_obat_ibfk_2` FOREIGN KEY (`id_obat`) REFERENCES `obat` (`id_obat`),
  ADD CONSTRAINT `supply_obat_ibfk_3` FOREIGN KEY (`id_user`) REFERENCES `user` (`id_user`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
