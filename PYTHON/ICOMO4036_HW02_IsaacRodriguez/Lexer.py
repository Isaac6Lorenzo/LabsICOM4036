# Isaac L. Rodriguez Pacheco
# ICOM 4036
# FEB 16, 2021

import sys
import ply.lex as lex

reserved = {
        'if': 'IF',
        'else': 'ELSE',
        'then': 'THEN',
        'let': 'LET',
        'in': 'IN',
        'map': 'MAP',
        'to': 'TO',
        'empty': 'EMPTY',
        'first': 'FIRST',
        'function': 'FUNCTION',
        'cons': 'CONS',
        'arity':'ARITY',
        'false': 'FALSE',
        'true': 'TRUE',
        'list': 'LIST',
        'rest': 'REST',
        'number': 'NUMBER'
}
        # 'while': 'WHILE',
        # 'not':'NOT',
        # 'and':'AND',
        # 'do': 'DO',
        # 'or':'OR'


#CICOM Definition
tokens = ['CHARACTER', 'DIGIT', 'DELIMITER', 'OPERATOR', 'RESERVED']
tokens = tokens + list(reserved)
lock = list(reserved)

def t_RESERVED(t):
    r'[a-z][a-z]+'
    if t.value in lock:
        # t.type = 'RESERVED'
        return t

def t_CHARACTER(t):
    r'[a-zA-Z] | [?] |[_]'
    # t.type = 'CHARACTER'
    return t

# t_CHARACTER =  r'[a-z] | [A-Z] | [?] |[_]'
t_DIGIT = r'[0-9]'
# t_DIGIT = r'[0] | [1] | [2] | [3] | [4] | [5] | [6] | [7] | [8] | [9]'
t_DELIMITER = r'[(] | [)] | [[] | []] | [,] | [;] | [{] | [}]'
t_OPERATOR = r'["+"] | [-] | [~] | ["*"] | [/] | [=] | [!=] | [<] | [>] | [<=] | [>=] | [&] | ["|"] | [:=]'
t_ignore = ' \t'
# t_RESERVED =

def t_error(t):
    print("Illegal character '%s'" % t.value[0])
    t.lexer.skip(1)



# =====================================================================
# mini test to lexer
# ruta = 'let f := map nap to if n = 0 then 1 else n * f(n - 1); ' \
#        ' in let f := map n,m,k to if (n <= 0 & n >= 0) | (n < 0 cons? empty function? -arity '

lexer = lex.lex()
# data = "? x char ) 5+3 while _"
# lexer.input(ruta)
#
# while True:
#     tok = lexer.token()
#     if not tok:
#         break
#     print(tok)