DELIMITER $$
CREATE DEFINER=`root`@`%` FUNCTION `avg_two_crypto`(first decimal(16,8), second decimal(16,8)) RETURNS decimal(16,8)
BEGIN
#RETURN CAST(AVG((first + second) / 2) AS DECIMAL(16,8));
RETURN (first + second) / 2;
END$$
DELIMITER ;
