TRUNCATE TABLE recipe; 

DELIMITER $$
DROP PROCEDURE IF EXISTS procedureName$$
 
CREATE PROCEDURE procedureName()
BEGIN
    DECLARE i INT DEFAULT 101;
        
    WHILE i <= 150 DO
        INSERT INTO recipe(title , content, writer, ingredient, registerdate, updateDate)
          VALUES( concat('키워드',i), '키워드 데이터 에요', '요리작가', '재료', now(),  now());
        SET i = i + 1;
    END WHILE;
END$$
DELIMITER $$

CALL procedureName; 
