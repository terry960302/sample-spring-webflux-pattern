--[INSERT] images
INSERT INTO IMAGES(url, width, height) VALUES ('https://user-images.githubusercontent.com/37768791/192220918-168f9002-883d-4d21-8b04-e8ac6273e8f6.png', 960, 641);
INSERT INTO IMAGES(url, width, height) VALUES ('https://user-images.githubusercontent.com/37768791/192221291-c9edc09d-1818-4617-8ca3-42b14d4161eb.png', 960, 641);

--[INSERT] users
INSERT INTO USERS(nickname, age, profileImgId) VALUES ('Terry', 21, 1);
INSERT INTO USERS(nickname, age, profileImgId) VALUES ('James', 35, 2);

--[INSERT] postings
INSERT INTO POSTINGS(userId, contents) VALUES (1, '허허허허허ㅓ헣ㅎ하하핳ㅎ핳하하ㅏㅏㅏㅎ하하ㅏㅏ 날씨좋네');
INSERT INTO POSTINGS(userId, contents) VALUES (2, 'askjdhashdahksdkjahsd weather like? blah blah~~~~');

--[INSERT] posting_images
INSERT INTO POSTING_IMAGES(postingId, imageId) VALUES (1, 1);
INSERT INTO POSTING_IMAGES(postingId, imageId) VALUES (1, 2);
INSERT INTO POSTING_IMAGES(postingId, imageId) VALUES (2, 1);
INSERT INTO POSTING_IMAGES(postingId, imageId) VALUES (2, 2);


--[INSERT] comments
INSERT INTO POSTING_COMMENTS(userId, postingId, contents) VALUES (1, 1, '안녕');
INSERT INTO POSTING_COMMENTS(userId, postingId, contents) VALUES (2, 1, '난 댓글이야');
INSERT INTO POSTING_COMMENTS(userId, postingId, contents) VALUES (1, 2, 'hi');
INSERT INTO POSTING_COMMENTS(userId, postingId, contents) VALUES (2, 2, 'i am comment man~');

