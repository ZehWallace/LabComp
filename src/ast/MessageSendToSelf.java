package ast;


public class MessageSendToSelf extends MessageSend {
    
    public Type getType() { 
        return null;
    }
    
    public void genC( PW pw, boolean putParenthesis ) {
    }

    @Override
    void genKra(PW pw) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
}