# Isaac L. Rodriguez Pacheco
# ICOM 4036
# FEB 16, 2021

import sys
from Parser import parser
from Lexer import lexer
from pprint import pprint


def main():
    with open("test.txt", 'r') as input_file:
        parser.parse(input_file.read())
        # book = input_file.read()
        # lexer.lexer.input(book)

    print("If nothing is printed above, there have been no errors")

if __name__ == '__main__':
    main()
