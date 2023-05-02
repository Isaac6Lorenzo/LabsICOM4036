# Isaac L. Rodriguez Pacheco
# ICOM 4036
# FEB 16, 2021

import ply.yacc as yacc
from pip._vendor.pyparsing import empty
import Lexer

tokens = Lexer.tokens

# procedence = ()


# reglas gramaticales

def p_Exp1(p):
   # Exp : Term { Binop Exp } | if Exp then Exp else Exp | let Def+ in Exp | map IdList to Exp
   '''
   Exp : Term DELIMITER Binop Exp DELIMITER
   '''
   # if p[2] == "(" and p[5] == ")":
   # if p[2] == "{" and p[5] == "}":
   # if p[2] == "[" and p[5] == "]":

def p_Exp2(p):
   '''
   Exp : if Exp then Exp else Exp
   '''
   if p[2] is True:
      p[0] = p[4]
   else:
      p[0] = p[6]

def p_Exp3(p):
   '''
   Exp : let Def in Exp
   '''

def p_Exp4(p):
   '''
   Exp : map IdList to Exp
   '''

def p_Term1(p):
   # Term : Unop Term | Factor { ( ExpList ) } | Empty | Int | Bool
   '''
   Term : Unop Term
   '''
   if p[1] == "+": p[0] = "+" + p[2]
   if p[1] == "-": p[0] = "-" + p[2]
   if p[1] == "~": p[0] = "~" + p[2]

def p_Term2(p):
   '''
   Term : Factor DELIMITER DELIMITER  ExpList DELIMITER DELIMITER
   '''

def p_Term3(p):
   '''
   Term : Empty
   '''
   p[0] = p[1]

def p_Term4(p):
   '''
   Term : Int
   '''
   p[0] = p[1]

def p_Term5(p):
   '''
   Term : Bool
   '''
   p[0] = p[1]

def p_Factor1(p):
   # Factor : ( Exp ) | Prim | Id
   '''
   Factor : DELIMITER Exp DELIMITER
   '''
   if p[1] == "("and p[3] == ")": p[0] = p[2]
   if p[1] == "{"and p[3] == "}": p[0] = p[2]
   if p[1] == "["and p[3] == "]": p[0] = p[2]

def p_Factor2(p):
   '''
   Factor : Prim
   '''
   p[0] = p[1]


def p_Factor3(p):
   '''
   Factor : Id
   '''
   p[0] = p[1]

def p_ExpList(p):
   # ExpList: {PropExpList}
   '''
   ExpList : DELIMITER PropExpList DELIMITER
   '''
   if p[1] == "("and p[3] == ")": p[0] = p[2]
   if p[1] == "{"and p[3] == "}": p[0] = p[2]
   if p[1] == "["and p[3] == "]": p[0] = p[2]


def p_PropExpList1(p):
   # PropExpList: Exp | Exp, PropExpList
   '''
   PropExpList : Exp
   '''
   p[0] = p[1]

def p_PropExpList2(p):
   '''
   PropExpList : Exp DELIMITER PropExpList
   '''
   if p[2] == ",": p[0] = [p[1], p[3]]

def p_IdList(p):
   # { PropIdList }
   '''
   IdList : DELIMITER PropIdList DELIMITER
   '''
   if p[1] == "("and p[3] == ")": p[0] = p[2]
   if p[1] == "{"and p[3] == "}": p[0] = p[2]
   if p[1] == "["and p[3] == "]": p[0] = p[2]



def p_PropIdList1(p):
   # PropIdList : Id | Id , PropIdList
   '''
   PropIdList : Id
   '''
   p[0] = p[1]

def p_PropIdList2(p):
   '''
   PropIdList : Id DELIMITER PropIdList
   '''
   if p[2] == ",":
      p[0] = [p[1], p[2]]


def p_Def1(p):
   # Def : Id := Exp ;
   '''
   Def : Id OPERATOR Exp DELIMITER
   '''
   if p[2] == ":=" and p[4] == ";":
      p[1] = p[3]
      p[0] = p[1]

# def p_Def2(p):
#    '''
#    Def : Exp DELIMITER
#    '''
#    if p[2] == ";": p[0] = p[1]


def p_Empty(p):
   ''' Empty : '''
   p[0] = empty()

def p_Bool1(p):
   # Bool : true | false
   '''
   Bool : true
   '''
   p[0] = p[1]

def p_Bool2(p):
   '''
   Bool : false
   '''
   p[0] = p[1]

def p_Unop1(p):
   # Unop : Sign | ~
   '''
   Unop : Sign
   '''
   p[0] = p[1]

def p_Unop2(p):
   # Unop: ~
   '''
   Unop : OPERATOR
   '''
   if p[0] == "~": p[0] = "~"

def p_Sign1(p):
   # Sign : "+" | -
   '''
   Sign : "+"
        | -
   '''
   if p[0] == "+": p[0] = "+"
   if p[0] == "-": p[0] = "-"

# def p_Sign2(p):
#    '''
#    Sign : -
#    '''

def p_Binop1(p):
   # Binop : Sign | "*" | / | = | != | < | > | <= | >= | & | "|"
   '''
   Binop : Sign
   '''
   p[0] = p[1]

def p_Binop2(p):
   # Binop : "*" | / | = | != | < | > | <= | >= | & | "|"
   '''
   Binop : DELIMITER
   '''
   if p[0] == "*": p[0] = "*"
   if p[0] == "/": p[0] = "/"
   if p[0] == "=": p[0] = "="
   if p[0] == "!=": p[0] = "!="
   if p[0] == "<": p[0] = "<"
   if p[0] == ">": p[0] = ">"
   if p[0] == "<=": p[0] = "<="
   if p[0] == ">=": p[0] = ">="
   if p[0] == "&": p[0] = "&"
   if p[0] == "|": p[0] = "|"

def p_Prim(p):
   '''
   Prim : number
        | function
        | list
        | empty
        | cons
        | first
        | rest
        | arity
   '''

def p_Id(p):
   # Id: CHARACTER {CHARACTER | DIGIT} *
   '''
   Id : CHARACTER DELIMITER CHARACTER OPERATOR DIGIT DELIMITER OPERATOR
   '''

def p_Int(p):
   '''
   Int : DIGIT
   '''
   p[0] = p[1]

def p_error(p):
   print("Syntax error in: " + p.type)
   yacc.errok()


parser = yacc.yacc()