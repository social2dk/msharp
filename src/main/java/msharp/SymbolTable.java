package msharp;

import msharp.Nodes.NumDeclNode;
import msharp.Nodes.PartDclNode;
import msharp.Nodes.ProgNode;

import java.util.*;

public class SymbolTable {
    public class Symbol<T>{
        public T value;

        Symbol(String id, T value){
            this.value = value;
        }


    }
    class Scope{
        Map<String,Symbol> localSymbols = new HashMap<>();
    }
    
    Stack<Scope> scopes = new Stack<>();
    
    public void openScope(){
        scopes.push(new Scope());
    }
    
    public void closeScope(){
        scopes.pop();
    }
    
    public <T> void  enterSymbol(String key, T value) throws IllegalCompilerAction
    {
        SymbolTable.Symbol symbol = retrieveSymbol(key);
        if(symbol == null){
            // not in symboltable, add to symboltable
            scopes.peek().localSymbols.put(key,new Symbol(key,value));
        }
        else symbol.value = value;
    }
    
    public Symbol retrieveSymbol(String key) throws IllegalCompilerAction
    {
        // take from top to stack to bottom of stack, search every scope if they have this variable.
        Symbol found = null;
        
        // the loop direction (bottom-top or top-bottom) on the stack doesnt matter,
        // when we dont allow multiple variables in different scopes with the same name.
        for (Iterator<Scope> it = scopes.iterator(); it.hasNext() && found == null; ) {
            Scope scope = it.next();
            
            if(scope.localSymbols.containsKey(key))
                found = scope.localSymbols.get(key);
        }
        return found;
    }
    
    
    public SymbolTable (ProgNode ast) throws IllegalCompilerAction
    {
        Scope scope = new Scope();
        
        for(NumDeclNode numNode : ast.getGlobalVariables()){
            if(scope.localSymbols.containsKey(numNode.getId()))
                throw new IllegalCompilerAction("When adding "+numNode.getId()+" to symboltable. Multiple declared");
            else {
                NumberExpressionVisitor visitor = new NumberExpressionVisitor();
                scope.localSymbols.put(numNode.getId(), new Symbol(numNode.getId(),numNode.getValue().accept(visitor,this)));
            }
        }
        for(PartDclNode partNode : ast.getParts()){
            if(scope.localSymbols.containsKey(partNode.getId()))
                throw new IllegalCompilerAction("When adding "+partNode.getId()+" to symboltable. Multiple declared");
            else {
                NumberExpressionVisitor visitor = new NumberExpressionVisitor();
                scope.localSymbols.put(partNode.getId(), new Symbol(partNode.getId(),partNode.getStmts()));
            }
        }
        
        scopes.push(scope);
    }
}
