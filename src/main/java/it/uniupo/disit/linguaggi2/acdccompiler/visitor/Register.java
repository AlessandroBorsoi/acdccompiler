package it.uniupo.disit.linguaggi2.acdccompiler.visitor;

class Register {
    private static char current = 0;

    static char newRegister() {
        return current++;
    }
}
