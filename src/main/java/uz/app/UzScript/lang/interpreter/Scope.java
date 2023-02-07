package uz.app.UzScript.lang.interpreter;

import java.util.Collection;

import uz.app.UzScript.lang.*;

public class Scope {
    /*private Collection<Variable> variables;
    private Scope parentScope;
    public Scope(Scope parentScope) {
        this.parentScope = parentScope;
    }

    public boolean hasVariable(String varName) {
        if(varName == null)
            return false;
        for (Variable variable : variables) {
            if(variable.getName().equals(varName))
                return true;
        }
        if(parentScope != null)
            return parentScope.hasVariable(varName);
        else
            return false;
    }

    public Variable getVariable(String varName) {
        if(varName == null)
            return null;
        for (Variable variable : variables) {
            if(variable.getName().equals(varName))
                return variable;
        }
        
        if(parentScope != null)
            return parentScope.getVariable(varName);
        else
            return null;
    }

    public Variable setVariable(String varName, Object value) {
        Variable newVar = new Variable(varName, value);
        variables.add(newVar);
        return newVar;
    }*/
}
