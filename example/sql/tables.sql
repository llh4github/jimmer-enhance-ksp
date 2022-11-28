
/*
 Navicat Premium Data Transfer

 Source Server         : local-pg-db
 Source Server Type    : PostgreSQL
 Source Server Version : 140005
 Source Host           : localhost:5432
 Source Catalog        : jimmer_test
 Source Schema         : public

 Target Server Type    : PostgreSQL
 Target Server Version : 140005
 File Encoding         : 65001

 Date: 28/11/2022 15:50:30
*/


-- ----------------------------
-- Sequence structure for author_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."author_id_seq";
CREATE SEQUENCE "public"."author_id_seq"
    INCREMENT 1
    MINVALUE  1
    MAXVALUE 2147483647
    START 1
    CACHE 1;

-- ----------------------------
-- Sequence structure for book_author_link_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."book_author_link_id_seq";
CREATE SEQUENCE "public"."book_author_link_id_seq"
    INCREMENT 1
    MINVALUE  1
    MAXVALUE 2147483647
    START 1
    CACHE 1;

-- ----------------------------
-- Sequence structure for book_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."book_id_seq";
CREATE SEQUENCE "public"."book_id_seq"
    INCREMENT 1
    MINVALUE  1
    MAXVALUE 2147483647
    START 1
    CACHE 1;

-- ----------------------------
-- Sequence structure for book_store_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."book_store_id_seq";
CREATE SEQUENCE "public"."book_store_id_seq"
    INCREMENT 1
    MINVALUE  1
    MAXVALUE 2147483647
    START 1
    CACHE 1;

-- ----------------------------
-- Table structure for author
-- ----------------------------
DROP TABLE IF EXISTS "public"."author";
CREATE TABLE "public"."author" (
                                   "id" int4 NOT NULL DEFAULT nextval('author_id_seq'::regclass),
                                   "english_name" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
                                   "chinese_name" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
                                   "gender" int2 NOT NULL
)
;
COMMENT ON COLUMN "public"."author"."id" IS ' ';

-- ----------------------------
-- Table structure for book
-- ----------------------------
DROP TABLE IF EXISTS "public"."book";
CREATE TABLE "public"."book" (
                                 "id" int4 NOT NULL DEFAULT nextval('book_id_seq'::regclass),
                                 "name" varchar(255) COLLATE "pg_catalog"."default",
                                 "edition" int2,
                                 "price" numeric(10,2) NOT NULL,
                                 "store_id" int4
)
;

-- ----------------------------
-- Table structure for book_author_link
-- ----------------------------
DROP TABLE IF EXISTS "public"."book_author_link";
CREATE TABLE "public"."book_author_link" (
                                             "id" int4 NOT NULL DEFAULT nextval('book_author_link_id_seq'::regclass),
                                             "author_id" int4 NOT NULL,
                                             "book_id" int4 NOT NULL
)
;

-- ----------------------------
-- Table structure for book_store
-- ----------------------------
DROP TABLE IF EXISTS "public"."book_store";
CREATE TABLE "public"."book_store" (
                                       "id" int4 NOT NULL DEFAULT nextval('book_store_id_seq'::regclass),
                                       "name" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
                                       "web_site" varchar(255) COLLATE "pg_catalog"."default"
)
;

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
ALTER SEQUENCE "public"."author_id_seq"
    OWNED BY "public"."author"."id";
SELECT setval('"public"."author_id_seq"', 1, true);

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
ALTER SEQUENCE "public"."book_author_link_id_seq"
    OWNED BY "public"."book_author_link"."id";
SELECT setval('"public"."book_author_link_id_seq"', 1, false);

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
ALTER SEQUENCE "public"."book_id_seq"
    OWNED BY "public"."book"."id";
SELECT setval('"public"."book_id_seq"', 4, true);

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
ALTER SEQUENCE "public"."book_store_id_seq"
    OWNED BY "public"."book_store"."id";
SELECT setval('"public"."book_store_id_seq"', 1, false);

-- ----------------------------
-- Primary Key structure for table author
-- ----------------------------
ALTER TABLE "public"."author" ADD CONSTRAINT "author_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table book
-- ----------------------------
ALTER TABLE "public"."book" ADD CONSTRAINT "book_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table book_author_link
-- ----------------------------
ALTER TABLE "public"."book_author_link" ADD CONSTRAINT "book_author_link_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table book_store
-- ----------------------------
ALTER TABLE "public"."book_store" ADD CONSTRAINT "book_store_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Foreign Keys structure for table book
-- ----------------------------
ALTER TABLE "public"."book" ADD CONSTRAINT "book_store_id_fkey" FOREIGN KEY ("store_id") REFERENCES "public"."book_store" ("id") ON DELETE CASCADE ON UPDATE CASCADE;

-- ----------------------------
-- Foreign Keys structure for table book_author_link
-- ----------------------------
ALTER TABLE "public"."book_author_link" ADD CONSTRAINT "book_author_link_author_id_fkey" FOREIGN KEY ("author_id") REFERENCES "public"."author" ("id") ON DELETE CASCADE ON UPDATE CASCADE;
ALTER TABLE "public"."book_author_link" ADD CONSTRAINT "book_author_link_book_id_fkey" FOREIGN KEY ("book_id") REFERENCES "public"."book" ("id") ON DELETE CASCADE ON UPDATE CASCADE;
