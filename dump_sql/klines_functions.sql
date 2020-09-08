DELIMITER $$
CREATE DEFINER=`root`@`%` FUNCTION `avg_two_crypto`(first decimal(16,8), second decimal(16,8)) RETURNS decimal(16,8)
BEGIN
#RETURN CAST(AVG((first + second) / 2) AS DECIMAL(16,8));
RETURN (first + second) / 2;
END$$
DELIMITER ;

DELIMITER $$
CREATE DEFINER=`root`@`%` FUNCTION `get_firstdat`(paras varchar(30)) RETURNS varchar(30) CHARSET latin1
BEGIN
RETURN (SELECT CONCAT(open_milliseconds, '__', SUBSTRING(time_open,1,10)) as firstday from kline_1d where open_milliseconds = (SELECT min(open_milliseconds) FROM klines.kline_1d where para = paras) and para = paras);
END$$
DELIMITER ;
