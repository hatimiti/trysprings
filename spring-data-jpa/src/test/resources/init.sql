INSERT INTO M_USER (M_USER_ID, NAME, PASSWORD) VALUES (1, 'user name 1', 'aaaaaaaa'); call next value for hibernate_sequence;
INSERT INTO M_USER (M_USER_ID, NAME, PASSWORD) VALUES (2, 'user name 2', 'aaaaaaaa'); call next value for hibernate_sequence;

INSERT INTO M_PLAN (M_PLAN_ID, PLAN_NAME, CONTENT) VALUES (1, 'Plan A', 'sample a'); call next value for hibernate_sequence;
INSERT INTO M_PLAN (M_PLAN_ID, PLAN_NAME, CONTENT) VALUES (2, 'Plan B', 'sample b'); call next value for hibernate_sequence;
INSERT INTO M_PLAN (M_PLAN_ID, PLAN_NAME, CONTENT) VALUES (3, 'Plan C', 'sample c'); call next value for hibernate_sequence;
INSERT INTO M_PLAN (M_PLAN_ID, PLAN_NAME, CONTENT) VALUES (4, 'Plan D', 'sample d'); call next value for hibernate_sequence;
INSERT INTO M_PLAN (M_PLAN_ID, PLAN_NAME, CONTENT) VALUES (5, 'Plan E', 'sample e'); call next value for hibernate_sequence;

INSERT INTO T_RESERVE (T_RESERVE_ID, RESERVE_NO, M_USER_ID) VALUES (1, 'XASISJA+KDn', 1); call next value for hibernate_sequence;
INSERT INTO T_RESERVE_ITEM (T_RESERVE_ITEM_ID, T_RESERVE_ID, M_PLAN_ID, NOTE) VALUES (1, 1, 1, 'Day 1'); call next value for hibernate_sequence;
INSERT INTO T_RESERVE_ITEM (T_RESERVE_ITEM_ID, T_RESERVE_ID, M_PLAN_ID, NOTE) VALUES (2, 1, 2, 'Day 2'); call next value for hibernate_sequence;
INSERT INTO T_RESERVE_ITEM (T_RESERVE_ITEM_ID, T_RESERVE_ID, M_PLAN_ID, NOTE) VALUES (3, 1, 1, 'Day 3'); call next value for hibernate_sequence;

INSERT INTO T_RESERVE (T_RESERVE_ID, RESERVE_NO, M_USER_ID) VALUES (2, 'NIlasd;aai1', 1); call next value for hibernate_sequence;
INSERT INTO T_RESERVE_ITEM (T_RESERVE_ITEM_ID, T_RESERVE_ID, M_PLAN_ID, NOTE) VALUES (4, 2, 1, 'Day 1'); call next value for hibernate_sequence;
INSERT INTO T_RESERVE_ITEM (T_RESERVE_ITEM_ID, T_RESERVE_ID, M_PLAN_ID, NOTE) VALUES (5, 2, 2, 'Day 2'); call next value for hibernate_sequence;
INSERT INTO T_RESERVE_ITEM (T_RESERVE_ITEM_ID, T_RESERVE_ID, M_PLAN_ID, NOTE) VALUES (6, 2, 3, 'Day 3'); call next value for hibernate_sequence;
INSERT INTO T_RESERVE_ITEM (T_RESERVE_ITEM_ID, T_RESERVE_ID, M_PLAN_ID, NOTE) VALUES (7, 2, 1, 'Day 4'); call next value for hibernate_sequence;

INSERT INTO T_RESERVE (T_RESERVE_ID, RESERVE_NO, M_USER_ID) VALUES (3, 'NPALKha;sdi', 2); call next value for hibernate_sequence;
INSERT INTO T_RESERVE_ITEM (T_RESERVE_ITEM_ID, T_RESERVE_ID, M_PLAN_ID, NOTE) VALUES ( 8, 3, 1, 'Day 1'); call next value for hibernate_sequence;
INSERT INTO T_RESERVE_ITEM (T_RESERVE_ITEM_ID, T_RESERVE_ID, M_PLAN_ID, NOTE) VALUES ( 9, 3, 2, 'Day 2'); call next value for hibernate_sequence;
INSERT INTO T_RESERVE_ITEM (T_RESERVE_ITEM_ID, T_RESERVE_ID, M_PLAN_ID, NOTE) VALUES (10, 3, 3, 'Day 3'); call next value for hibernate_sequence;
INSERT INTO T_RESERVE_ITEM (T_RESERVE_ITEM_ID, T_RESERVE_ID, M_PLAN_ID, NOTE) VALUES (11, 3, 1, 'Day 4'); call next value for hibernate_sequence;
