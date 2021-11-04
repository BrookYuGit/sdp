# SDP
SQL-driven programming

The SDP is a tool for generate source code, which base on SpringBoot and Mybatis.

The backend of SDP based on Mybatis generator plugin https://github.com/mybatis/generator.
The front end of SDP based on https://github.com/chuzhixin/vue-admin-beautiful-pro.

# SDP template language
SDP use template to generate source file.
The tempalate is very simple: without IF, without for. Only support two style:block and element.
The block is just like {*xxx}, and the element just like {yyy}.
Block is asa 'FOR' in other source code generate tool.
