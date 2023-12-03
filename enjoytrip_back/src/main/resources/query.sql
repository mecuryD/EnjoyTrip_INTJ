use enjoytrip;

-- 테이블 구성 확인
desc user;
desc attraction_info;
desc attraction_description;
desc search;
desc board;
desc comment;
desc sido;
desc gugun;
desc heart;

-- 테이블 데이터 확인
select * from user;
select * from attraction_info;
select * from attraction_description;
select * from search;
select * from gugun;
select * from board;                           
select * from comment;
select * from heart;
select * from plan;
select * from plan_info;


insert into board (boardType, userId, boardSubject, boardContent, boardView, boardCreateDate) 
		values (0, 'admin', '첫 번째 공지사항', '공지사항 내용', 0, now()),
        (0, 'admin', '두 번째 공지사항', '공지사항 내용', 0, now()),
        (0, 'admin', '세 번째 공지사항', '공지사항 내용', 0, now()),
        (0, 'admin', '네 번째 공지사항', '공지사항 내용', 0, now()),
        (0, 'admin', '다섯 번째 공지사항', '공지사항 내용', 0, now());


-- 찜하기
insert into heart(userId, contentId, heartCreateDate)
values ('ssafy', 125505, now());

-- 찜 여부 확인
select * from heart;

-- 찜하기 해제
delete from heart
where userId='admin'
and contentId=125505;

-- 댓글 등록
insert into comment(boardId, userId, commentContent, commentCreatedDate)
values (1, 'admin', '그러니 빠른 시일 내에 삭제 부탁드립니다', now()), (1, 'admin', '삭제 안하냐', now()); 

-- 댓글 조회
select userId, commentId, commentContent, commentCreatedDate
from comment
where boardId = 1
order by commentCreatedDate asc;
        
-- 댓글 삭제
delete from comment
where commentId = 4;

-- 시도 코드 조회
select sidoCode, sidoName
from sido;

-- 구군 코드 조회
select gugunCode, gugunName, sidoCode
from gugun
where sidoCode = 3;

-- 여행지 검색 조회
select contentId, contentTypeId, title, addr1, addr2, zipcode, tel, firstImage, sidoCode, gugunCode, latitude, longitude
from attraction_info
where sidoCode=3
and title like '%바다%';

-- 여행지 상세 조회
select overview
from attraction_description
where contentId=125411;

-- 여행지 검색 데이터 저장
insert into search(userId, birth, gender, sidoCode, gugunCode, contentTypeId, searchContent)
values (
'ssafy',
(select birth from user where userid='ssafy'),
(select gender from user where userid='ssafy'),
null, null, 12, '테스트3');

-- 사용자 찜 리스트 조회
select i.contentId, i.contentTypeId, i.title, i.addr1, i.addr2, i.zipcode, i.tel, i.firstImage, i.sidoCode, i.gugunCode, i.latitude, i.longitude
from heart h inner join attraction_info i
on  h.contentId = i.contentId
where h.userId = 'ssafy'
order by h.heartCreateDate;