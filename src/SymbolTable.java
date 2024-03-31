import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class SymbolTable {
    private final Stack<Map<String, Integer>> scopeStack; // 스코프 스택

    public SymbolTable() {
        this.scopeStack = new Stack<>();
        // 전역 스코프 추가
        enterScope();
    }

    // 새로운 스코프 진입
    public void enterScope() {
        scopeStack.push(new HashMap<>());
    }

    // 스코프 나가기
    public void exitScope() {
        scopeStack.pop();
    }

    // 변수 추가
    public void addVariable(String name, int addr) {
        Map<String, Integer> currentScope = scopeStack.peek();
        currentScope.put(name, addr);
    }

    // 변수 조회 (현재 스코프에서부터 찾음)
    public int getVariable(String name) {
        for (int i = scopeStack.size() - 1; i >= 0; i--) {
            Map<String, Integer> currentScope = scopeStack.get(i);
            if (currentScope.containsKey(name)) {
                return currentScope.get(name);
            }
        }
        return -1;
    }
}

