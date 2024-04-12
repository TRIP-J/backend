CREATE TABLE item_cate (
    item_cate_id INT AUTO_INCREMENT PRIMARY KEY,
    item_cate_code CHAR(3) NOT NULL,
    item_cate_name VARCHAR(255) NOT NULL
);

CREATE TABLE country (
    country_id INT AUTO_INCREMENT PRIMARY KEY,
    country_code CHAR(2) NOT NULL,
    country_name VARCHAR(255) NOT NULL
);

CREATE TABLE item (
    item_id INT AUTO_INCREMENT PRIMARY KEY,
    item_name VARCHAR(255) NOT NULL,
    item_cate_id INT,
    fix CHAR(1),
    FOREIGN KEY (item_cate_id) REFERENCES item_cate(item_cate_id)
);

INSERT INTO item_cate (item_cate_id, item_cate_code, item_cate_name)
VALUES (1, 'REC', '추천템'),
       (2, 'ESS', '필수품'),
       (3, 'CLO', '의류'),
       (4, 'BEA', '미용'),
       (5, 'ELE', '전자기기'),
       (6, 'PRI', '출력물');

INSERT INTO country (country_id, country_code, country_name)
VALUES (1, 'JP', '일본'),
       (2, 'CN', '중국'),
       (3, 'TH', '태국'),
       (4, 'HK', '홍콩'),
       (5, 'EG', '영국'),
       (6, 'FR', '프랑스'),
       (7, 'IT', '이탈리아'),
       (8, 'AM', '미국'),
       (9, 'CA', '캐나다'),
       (10, 'AR', '아르헨티나');

INSERT INTO item (item_id, item_name, item_cate_id, fix, country_id)
VALUES (1, '여권', 2, 'F', null),
         (2, '지갑', 2, 'F', null),
         (3, '유심', 2, 'F', null),
         (4, '신분증', 2, 'F', null),
         (5, '현지화폐', 2, 'F', null),
         (6, '신용카드', 2, 'F', null),
         (7, '110v돼지코', 1, 'F', 1),
         (8, '동전지갑', 1, 'F', 1),
         (9, '상비약', 1, 'F', 2),
         (10, 'VPN앱', 1, 'F', 2),
         (11, '선글라스', 1, 'F', 3),
         (12, '선크림', 1, 'F', 3),
         (13, '아쿠아 슈즈', 1, 'F', 3),
         (14, '모기 퇴치제', 1, 'F', 3),
         (15, '손풍기', 1, 'F', 4),
         (16, '빈대 퇴치제', 1, 'F', 6),
         (17, '필터 샤워기', 1, 'F', 6),
         (18, '선글라스', 1, 'F', 7),
         (19, '선크림', 1, 'F', 7);