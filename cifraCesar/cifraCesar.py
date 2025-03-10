resultado = ''

escolha1 = int(input('Você deseja: [1] para ver uma mensagem criptografada e [2] para ver uma mensagem descriptografada: '))
while escolha1 != 1 and escolha1 != 2:
    print('\033[31mErro, você só pode escolher entre 1 e 2!\033[0m')
    escolha1 = int(input('Você deseja: [1] para ver uma mensagem criptografada e [2] para ver uma mensagem descriptografada: '))

if escolha1 == 1:
    escolha2 = int(input('Quantos caracteres deseja pular:'))
    while escolha2 <= 0 or escolha2 > 9:
        print('\033[31mErro, você deve escolher apenas números até o 9 acima de zero!\033[0m')
        escolha2 = int(input('Quantos caracteres deseja pular:'))

if escolha1 == 1:
    with open("cripto", "r") as f:
        texto = f.read()

    for i in range(0,len(texto)):
        resultado = resultado + chr(ord(texto[i]) + escolha2)
        print(f'\033[32m{resultado}\033[0m', end='')
        resultado = ''

if escolha1 == 2:
    with open("descripto", "r") as f:
        texto = f.read()

    for i in range(0,len(texto)):
        resultado = resultado + chr(ord(texto[i]) - 3)
        print(f'\033[32m{resultado}\033[0m', end='')
        resultado = ''