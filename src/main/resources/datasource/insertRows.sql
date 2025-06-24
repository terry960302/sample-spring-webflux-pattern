--[INSERT] images
insert into images (file_name, width, height, file_size, mime_type, uploaded_at, url)
values ('192220918-168f9002-883d-4d21-8b04-e8ac6273e8f6.png', 960, 641, 245760, 'image/png', NOW(), 'https://user-images.githubusercontent.com/37768791/192220918-168f9002-883d-4d21-8b04-e8ac6273e8f6.png');
insert into images (file_name, width, height, file_size, mime_type, uploaded_at, url)
values ('192221291-c9edc09d-1818-4617-8ca3-42b14d4161eb.png', 960, 641, 198400, 'image/png', NOW(), 'https://user-images.githubusercontent.com/37768791/192221291-c9edc09d-1818-4617-8ca3-42b14d4161eb.png');

--[INSERT] users
INSERT INTO users (username, age, email, profile_img_id, created_at)
VALUES ('alice_kim', 28, 'alice.kim@example.com', 1, NOW());
INSERT INTO users (username, age, email, profile_img_id, created_at)
VALUES ('bob_lee', 32, 'bob.lee@example.com', NULL, NOW());

--[INSERT] user_credentials
INSERT INTO user_credentials (user_id, role, email, password, created_at, updated_at, last_login_at, is_active)
VALUES (1, 'USER', 'alice.kim@example.com', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2uheWG/igi.', NOW(), NOW(), NOW() - INTERVAL '2 hours', TRUE);
INSERT INTO user_credentials (user_id, role, email, password, created_at, updated_at, last_login_at, is_active)
VALUES (2, 'ADMIN', 'bob.lee@example.com', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2uheWG/igi.', NOW(), NOW(), NULL, TRUE);

--[INSERT] postings
insert into POSTINGS(user_id, contents) values (1, '허허허허허ㅓ헣ㅎ하하핳ㅎ핳하하ㅏㅏㅏㅎ하하ㅏㅏ 날씨좋네');
insert into POSTINGS(user_id, contents) values (2, 'askjdhashdahksdkjahsd weather like? blah blah~~~~');

--[INSERT] posting_images
insert into POSTING_IMAGES(posting_id, image_id) values (1, 1);
insert into POSTING_IMAGES(posting_id, image_id) values (1, 2);
insert into POSTING_IMAGES(posting_id, image_id) values (2, 1);
insert into POSTING_IMAGES(posting_id, image_id) values (2, 2);

--[INSERT] comments
insert into POSTING_COMMENTS(user_id, posting_id, contents) values (1, 1, '안녕');
insert into POSTING_COMMENTS(user_id, posting_id, contents) values (2, 1, '난 댓글이야');
insert into POSTING_COMMENTS(user_id, posting_id, contents) values (1, 2, 'hi');
insert into POSTING_COMMENTS(user_id, posting_id, contents) values (2, 2, 'i am a comment man~');

