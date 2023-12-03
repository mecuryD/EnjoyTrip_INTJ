select * from user;
desc user;
-- FOREIGN_KEY_CHECK 무시
set FOREIGN_KEY_CHECKS = 0;
truncate board;
set FOREIGN_KEY_CHECKS = 1;

desc comment;
select * from comment;
-- 회원 등록
insert into user (level, userId, userPwd, userName, birth, gender, emailId, emailDomain, joinDate)
values
(1,'ssafy','1234','킴싸피','1995-10-12',2,'ssafy','ssafy.com', now()),
(1,'leessafy','1234','이싸피','1997-12-25',2,'leessafy','ssafy.com', now()),
(1,'baessafy','1234','배싸피','1998-01-05',2,'baessafy','naver.com', now()),
(1,'sonssafy','1234','손싸피','1998-08-15',1,'sonssafy','gmail.com', now()),
(1,'parkssafy','1234','박싸피','2002-02-02',1,'parkssafy','ssafy.com', now());

-- heart
desc heart;
select * from comment;
select * from heart;

insert into heart(userId, contentId, heartCreateDate)
values
('leessafy',125468,now()),
('leessafy',125471,now()),
('leessafy',125535,now()),
('leessafy',125561,now()),
('leessafy',125584,now()),
('leessafy',125616,now()),
('leessafy',125644,now()),
('leessafy',125673,now()),
('sonssafy',125741,now()),
('sonssafy', 125760,now()),
('sonssafy',125761 ,now()),
('sonssafy', 125780,now()),
('sonssafy',125801 ,now()),
('sonssafy', 125835,now()),
('sonssafy', 125856,now()),
('sonssafy',125883,now()),
('sonssafy',125906 ,now());

-- 여행 계획 등록 : 소개글
select * from plan;
select * from gugun;
insert into plan(userId, planName, startDate, endDate, planMemo)
values
('kimssafy', '이태원, 다문화와 트렌디한 문화가 공존하는 서울의 보석', '2023-11-24 23:59:00', '2023-11-01 06:00:00', '이태원 거리에는 다양한 나라의 레스토랑, 상점, 그리고 예술 공간들이 즐비하게 자리하고 있어, 다채로운 문화를 만끽할 수 있습니다.'),
('sonssafy', '대전, 현대과 전통이 공존하는 도시', '2023-11-22 12:00:00', '2023-11-23 13:00:00', '대전의 대표적인 명소인 대전시연합도서관과 대전둘레길은 문화와 자연을 함께 느낄 수 있는 곳으로, 산책이나 문화 행사를 통해 도시의 매력을 만끽할 수 있습니다.'),
('leessafy', '푸른 바다와 신선한 바다향, 동해의 아름다움을 만끽하세요.', '2023-11-24 23:59:00', '2023-11-01 06:00:00', '동해는 신선한 해산물과 풍부한 자연이 어우러진 곳으로, 매력적인 여행지로 여러분을 초대합니다. 바다의 품에서 신선한 공기를 마시며 힐링의 여행을 떠나보세요.'),
('baessafy', '비경의 정상, 한라산 여행', '2022-11-24 12:59:00', '2023-11-01 06:00:00', '신비로운 계곡과 다양한 식물이 어우러진 한라산은 자연을 사랑하는 이들에게 꼭 추천하는 여행지로, 산악인이든 일반 여행자든 새로운 경험과 감동을 선사하는 명산입니다.');

-- 여행 계획 등록 : 경로
select * from plan;
select * from plan_info;
insert into plan_info(planId, planOrder, contentId)
values
(4, 1, 125605),
(4, 2, 125626),
(4, 3, 125749);
(2, 4, 2852289);
(2, 5, 2786779);

-- 게시글 등록
desc board;
select * from user;
insert into board (boardType, userId, boardSubject, boardContent, boardView, boardCreateDate) 
values 
(1, 'parkssafy', '노르웨이의 오로라 매력', '지난 겨울, 노르웨이의 작은 항구 마을 트롬쏘에서 놀라운 오로라를 목격했습니다. 차가운 공기와 아름다운 풍경, 그리고 오로라의 놀라움을 동시에 느낄 수 있는 트롬쏘를 꼭 추천하는 곳입니다.', 0, now()),
(1, 'sonssafy', '인도네시아의 낙원, 발리의 감동', '발리는 자연의 아름다움과 문화적인 풍부함이 어우러진 곳으로, 여행 중 계속해서 놀라운 순간을 맛볼 수 있었습니다.작은 마을에서의 로컬 문화 체험도 잊을 수 없는 경험이었어요!', 0, now());

-- 댓글 등록
desc comment;
select * from comment;
insert into comment(boardId, userId, commentContent, commentCreatedDate)
values 
(1,'admin','이 글을 읽으니 마음이 따뜻해졌어요. 감사합니다!',now()),
(1,'sonssafy','이 정보 정말 유용해요. ',now() ),
(1,'parkssafy','앞으로 자주 참고하겠습니다.',now() ),
(1,'leessafy','이 사진 너무 아름다워요! 어떤 카메라를 사용하셨나요?',now() ),
(2,'admin','이 글을 읽으니 마음이 따뜻해졌어요. 감사합니다!',now() ),
(2,'sonssafy','이 정보 정말 유용해요. ',now() ),
(2,'parkssafy','앞으로 자주 참고하겠습니다.',now() ),
(2,'leessafy','이 사진 너무 아름다워요! 어떤 카메라를 사용하셨나요?',now() ),
(3,'admin','이 글을 읽으니 마음이 따뜻해졌어요. 감사합니다!',now() ),
(3,'sonssafy','이 정보 정말 유용해요. ',now() ),
(3,'parkssafy','앞으로 자주 참고하겠습니다.',now() ),
(3,'leessafy','이 사진 너무 아름다워요! 어떤 카메라를 사용하셨나요?',now() ),
(4,'admin','이 글을 읽으니 마음이 따뜻해졌어요. 감사합니다!',now() ),
(4,'sonssafy','이 정보 정말 유용해요. ' ,now()),
(4,'parkssafy','앞으로 자주 참고하겠습니다.',now() ),
(4,'leessafy','이 사진 너무 아름다워요! 어떤 카메라를 사용하셨나요?' ,now()),
(5,'admin','이 글을 읽으니 마음이 따뜻해졌어요. 감사합니다!',now() ),
(5,'sonssafy','이 정보 정말 유용해요. ' ,now()),
(5,'parkssafy','앞으로 자주 참고하겠습니다.',now() ),
(5,'leessafy','이 사진 너무 아름다워요! 어떤 카메라를 사용하셨나요?' ,now());

ALTER TABLE comment AUTO_INCREMENT = 21;

select * from comment;
