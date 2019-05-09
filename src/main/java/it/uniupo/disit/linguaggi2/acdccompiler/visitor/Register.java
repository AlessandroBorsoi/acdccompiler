package it.uniupo.disit.linguaggi2.acdccompiler.visitor;

class Register {
    private static char current = 'a';

    static void init() {
        current = 'a';
    }

    static char newRegister() {
        return current++;
    }
}
