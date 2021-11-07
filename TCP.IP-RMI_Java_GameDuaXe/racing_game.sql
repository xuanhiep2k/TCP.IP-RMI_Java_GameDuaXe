-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Nov 07, 2021 at 09:23 AM
-- Server version: 10.4.21-MariaDB
-- PHP Version: 7.4.25

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `racing_game`
--

-- --------------------------------------------------------

--
-- Table structure for table `tblapprovalgroup`
--

CREATE TABLE `tblapprovalgroup` (
  `id` int(20) NOT NULL,
  `idplayer` int(20) NOT NULL,
  `namePlayer` varchar(200) NOT NULL,
  `nameGroup` varchar(200) NOT NULL,
  `host` varchar(200) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `tblapprovalgroup`
--

INSERT INTO `tblapprovalgroup` (`id`, `idplayer`, `namePlayer`, `nameGroup`, `host`) VALUES
(86, 14, 'Vu Thuy Linh', 'abc', 'Tran Nhat Duy'),
(87, 14, 'Vu Thuy Linh', 'iu e', 'test');

-- --------------------------------------------------------

--
-- Table structure for table `tblfriendlist`
--

CREATE TABLE `tblfriendlist` (
  `id` int(20) NOT NULL,
  `id_player1` int(20) NOT NULL,
  `id_player2` int(20) NOT NULL,
  `namePlayer1` varchar(200) NOT NULL,
  `namePlayer2` varchar(200) NOT NULL,
  `stt1` varchar(20) NOT NULL,
  `stt2` varchar(202) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `tblfriendlist`
--

INSERT INTO `tblfriendlist` (`id`, `id_player1`, `id_player2`, `namePlayer1`, `namePlayer2`, `stt1`, `stt2`) VALUES
(81, 1, 17, 'Tran Xuan Hiep', 'test', '0', '0'),
(82, 2, 17, 'Tran Phuong Nam', 'test', '0', '0');

-- --------------------------------------------------------

--
-- Table structure for table `tblfriendrequest`
--

CREATE TABLE `tblfriendrequest` (
  `requestid` int(20) NOT NULL,
  `senderid` int(20) NOT NULL,
  `recieverid` int(20) NOT NULL,
  `requestname` varchar(200) NOT NULL,
  `status` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `tblfriendrequest`
--

INSERT INTO `tblfriendrequest` (`requestid`, `senderid`, `recieverid`, `requestname`, `status`) VALUES
(159, 1, 0, 'Tran Xuan Hiep', '0'),
(177, 17, 0, 'test', '0');

-- --------------------------------------------------------

--
-- Table structure for table `tblgroup`
--

CREATE TABLE `tblgroup` (
  `id` int(20) NOT NULL,
  `idplayer` int(20) NOT NULL,
  `namePlayer` varchar(20) NOT NULL,
  `nameGroup` varchar(20) NOT NULL,
  `host` varchar(200) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `tblgroup`
--

INSERT INTO `tblgroup` (`id`, `idplayer`, `namePlayer`, `nameGroup`, `host`) VALUES
(5, 16, 'Tran Nhat Duy', 'abc', 'Tran Nhat Duy'),
(20, 17, 'test', 'iu e', 'test'),
(21, 1, 'Tran Xuan Hiep', 'iu e', 'test'),
(23, 2, 'Tran Phuong Nam', 'iu e', 'test'),
(24, 15, 'Tran Duc The', 'Chua co ny', 'Tran Duc The');

-- --------------------------------------------------------

--
-- Table structure for table `tblplayer`
--

CREATE TABLE `tblplayer` (
  `id_player` int(20) NOT NULL,
  `username` varchar(200) NOT NULL,
  `password` varchar(200) NOT NULL,
  `fullname` varchar(200) NOT NULL,
  `gender` varchar(200) NOT NULL,
  `email` varchar(200) NOT NULL,
  `position` varchar(200) NOT NULL,
  `status` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `tblplayer`
--

INSERT INTO `tblplayer` (`id_player`, `username`, `password`, `fullname`, `gender`, `email`, `position`, `status`) VALUES
(1, '1', '1', 'Tran Xuan Hiep', 'Nam', 'tranxuanhiepcmd3@gmail.com', 'Adminstrator', '0'),
(2, '2', '1', 'Tran Phuong Nam', 'Male', '', 'Player', '0'),
(14, '3', '1', 'Vu Thuy Linh', 'Female', '', '', '0'),
(15, '4', '1', 'Tran Duc The', '', '', '', '0'),
(16, '5', '1', 'Tran Nhat Duy', '', '', '', '0'),
(17, '', '', 'test', '', '', '', '0'),
(21, '123', '123', '', 'Male', '', 'Player', '0');

-- --------------------------------------------------------

--
-- Table structure for table `tblrank`
--

CREATE TABLE `tblrank` (
  `idrank` int(20) NOT NULL,
  `idplayer` int(20) NOT NULL,
  `fullname` varchar(200) NOT NULL,
  `core` float NOT NULL,
  `rank` int(20) NOT NULL,
  `status` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `tblrank`
--

INSERT INTO `tblrank` (`idrank`, `idplayer`, `fullname`, `core`, `rank`, `status`) VALUES
(1, 1, 'Tran Xuan Hiep', 2, 0, '0'),
(2, 2, 'Tran Phuong Nam', 2, 0, '0'),
(7, 15, 'Tran Duc The', 5, 0, '0'),
(8, 14, 'Vu Thuy Linh', 1, 0, '0'),
(10, 16, 'Tran Nhat Duy', 7, 0, '0'),
(41, 17, 'test', 0, 0, '0'),
(43, 21, '', 0, 0, '0');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `tblapprovalgroup`
--
ALTER TABLE `tblapprovalgroup`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `tblfriendlist`
--
ALTER TABLE `tblfriendlist`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `tblfriendrequest`
--
ALTER TABLE `tblfriendrequest`
  ADD PRIMARY KEY (`requestid`);

--
-- Indexes for table `tblgroup`
--
ALTER TABLE `tblgroup`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `tblplayer`
--
ALTER TABLE `tblplayer`
  ADD PRIMARY KEY (`id_player`);

--
-- Indexes for table `tblrank`
--
ALTER TABLE `tblrank`
  ADD PRIMARY KEY (`idrank`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `tblapprovalgroup`
--
ALTER TABLE `tblapprovalgroup`
  MODIFY `id` int(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=90;

--
-- AUTO_INCREMENT for table `tblfriendlist`
--
ALTER TABLE `tblfriendlist`
  MODIFY `id` int(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=83;

--
-- AUTO_INCREMENT for table `tblfriendrequest`
--
ALTER TABLE `tblfriendrequest`
  MODIFY `requestid` int(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=186;

--
-- AUTO_INCREMENT for table `tblgroup`
--
ALTER TABLE `tblgroup`
  MODIFY `id` int(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=28;

--
-- AUTO_INCREMENT for table `tblplayer`
--
ALTER TABLE `tblplayer`
  MODIFY `id_player` int(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=23;

--
-- AUTO_INCREMENT for table `tblrank`
--
ALTER TABLE `tblrank`
  MODIFY `idrank` int(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=48;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
