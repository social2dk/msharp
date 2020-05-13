package msharp.Compiler;

public class IllegalCompilerAction extends RuntimeException {
    private final String error;
    
    public String getError ()
    {
        return error;
    }
    
    public IllegalCompilerAction (String error){
        this.error = error;
    }
}
