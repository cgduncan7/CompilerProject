/* Generated By:JavaCC: Do not edit this line. EG1.java */
import java.util.*;

public class EG1 implements EG1Constants {
  static boolean genCode = true;
  static int tempCounter, symbolCounter, nestingLevel, offset;
  private static Stack stack, breakStack, contStack;
  public static Stack blockStack;
  private static ArrayList<ExtendedQuad> eQuadList;
  static ArrayList<Symbol> symbolTable;
  private static ArrayList<Integer> basicBlockStarts;

  public static void main(String args []) throws ParseException
  {
    EG1 parser = new EG1(System.in);
    if (true)
    {
      stack = new Stack();
      breakStack = new Stack();
      blockStack = new Stack();
      blockStack.push("block0");
      contStack = new Stack();
      eQuadList = new ArrayList<ExtendedQuad>();
      symbolTable = new ArrayList<Symbol>();
      basicBlockStarts = new ArrayList<Integer>();
      tempCounter = 1;
      symbolCounter = 1;
      nestingLevel = 0;
      offset = 0;
      System.out.println("Reading from standard input...");
      System.out.print("$ > ");
      try
      {
        if (EG1.program())
        {
          if (genCode)
          {
                  //Debug print outs
                  System.out.println("\u005cn- Extended QuadList -");
                          int i = 0;
                          for (ExtendedQuad q : eQuadList)
                          {
                                if (i == 0)
                                {
                                  basicBlockStarts.add(i);
                                }
                                else if ("jump".equals(q.getOperator()))
                                {
                                  int next = Integer.parseInt(q.getResult().getName());
                                  if (basicBlockStarts.indexOf(next) == -1)
                                  {
                                    basicBlockStarts.add(next);
                                  }
                                }
                                else if ("jeqz".equals(q.getOperator()))
                                {
                                  int next = Integer.parseInt(q.getResult().getName());
                                  if (basicBlockStarts.indexOf(next) == -1)
                                  {
                                    basicBlockStarts.add(next);
                                  }
                                  if (i+1 < eQuadList.size()-1 && basicBlockStarts.indexOf(i+1) == -1) basicBlockStarts.add(i+1);
                                }
                            System.out.println(i++ + ") " + q.toString());
                          }

                          Collections.sort(basicBlockStarts);

                          System.out.println("\u005cn- Basic Block Starts  -");
                          for (Integer in : basicBlockStarts)
                          {
                            System.out.println(in.toString());
                          }

                          Collections.reverse(basicBlockStarts);
                          for (int x = 0; x < basicBlockStarts.size()-1; x++)
                          {
                            int quadNum = basicBlockStarts.get(x) - 1;
                            int nextStart = basicBlockStarts.get(x+1);
                            while (quadNum >= 0 && quadNum >= nextStart)
                            {
                              Field b = eQuadList.get(quadNum).getArg1();
                              Field c = eQuadList.get(quadNum).getArg2();
                              Field a = eQuadList.get(quadNum).getResult();
                              if (a.isPtr())
                              {
                                a.setNextUse(symbolTable.get(a.getSymbolTablePtr()).getNextUse());
                                symbolTable.get(a.getSymbolTablePtr()).setNextUse(0);
                              }

                              if (b.isPtr())
                              {
                                b.setNextUse(symbolTable.get(b.getSymbolTablePtr()).getNextUse());
                                symbolTable.get(b.getSymbolTablePtr()).setNextUse(quadNum);
                              }

                              if (c.isPtr())
                              {
                                c.setNextUse(symbolTable.get(c.getSymbolTablePtr()).getNextUse());
                                symbolTable.get(c.getSymbolTablePtr()).setNextUse(quadNum);
                              }

                              quadNum--;
                            }
                            System.out.println("\u005cn- Symbol Table for BB (Quad#" + (quadNum+1) + " to Quad#" + (basicBlockStarts.get(x)-1) + ") -");
                                int ii = 0;
                                for (Symbol s : symbolTable)
                                {
                                  System.out.println(ii++ + ") " + s.toString());
                                  s.setNextUse(-1);
                                }
                          }

                          System.out.println("\u005cn- Stack -");
                          for (Object o : stack)
                          {
                                System.out.println(o);
                          }

                          if (stack.size() == 0)
                                System.out.println("empty");
                  System.out.println("OK.");
                }
                else
                {
                        System.out.println("Errors detected! No code generation!");
                }
             }
      }
      catch (Exception e)
      {
        System.out.println("NOK.");
        e.printStackTrace();
      }
      catch (Error e)
      {
        System.out.println("Oops.");
        System.out.println(e.getMessage());
      }
    }
  }

  static final public boolean program() throws ParseException {
    jj_consume_token(LBRACE);
    label_1:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case FN:
        ;
        break;
      default:
        jj_la1[0] = jj_gen;
        break label_1;
      }
      function();
    }
    label_2:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case MINUS:
      case NOT:
      case LBRACE:
      case LPAREN:
      case SEMI:
      case BREAK:
      case CONT:
      case IF:
      case RET:
      case WHILE:
      case INT:
      case ID:
        ;
        break;
      default:
        jj_la1[1] = jj_gen;
        break label_2;
      }
      statement();
    }
    jj_consume_token(RBRACE);
            {if (true) return true;}
    throw new Error("Missing return statement in function");
  }

  static final public void function() throws ParseException {
  Token name;
    try {
      jj_consume_token(FN);
      name = jj_consume_token(ID);
                  //Add the main block to the Symbol Table
                  if (genCode) symbolTable.add(new Symbol(name.image, "MAIN", 0, -1, -1, -1));
      jj_consume_token(LPAREN);
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case ID:
        jj_consume_token(ID);
        break;
      default:
        jj_la1[2] = jj_gen;
        ;
      }
      jj_consume_token(RPAREN);
      compound_statement();
    } catch (ParseException e) {
          errorSkipTo(RBRACE);
    }
  }

  static final public void statement() throws ParseException {
  int nextQuad;
  Object o, p;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case LBRACE:
      try {
        compound_statement();
      } catch (ParseException e) {
          errorSkipBlock();
      }
      break;
    case WHILE:
      try {
        jj_consume_token(WHILE);
                  if (genCode)
                  {
                    stack.push(offset);
                        blockStack.push("block"+(symbolCounter));
                        symbolTable.add(new Symbol("block"+symbolCounter++,"block"+nestingLevel,++nestingLevel,-1,-1,-1));
                        offset = 0;
                    stack.push(eQuadList.size());
                        //Push the current quad to the continue stack to hold the location of the beginning
                        //of the while
                        contStack.push(eQuadList.size());
                  }
        test();
                  if (genCode)
                  {
                    o = stack.pop();
                    stack.push(eQuadList.size());
                        //Push next quad to the break stack for backpatching
                        breakStack.push(eQuadList.size());
                        eQuadList.add(new ExtendedQuad("jeqz", o.toString(), null, -1, -1));
                  }
        statement();
                  if (genCode)
                  {
                          o = stack.pop();
                          p = stack.pop();
                          eQuadList.add(new ExtendedQuad("jump", null, null, p.toString(), -1));
                          int whileJump = eQuadList.size();

                          int x = (int) breakStack.pop();
                          //Traverse through the quadlist and set the jump location to the quad number
                          //on the break stack
                          while (x != -1)
                          {
                            ExtendedQuad eq = eQuadList.get(x);
                            x = Integer.parseInt(eq.getResult().getName());
                            eq.setResult(new Field(whileJump+""));
                          }

                          contStack.pop();
                          nestingLevel--;
                          offset = (int) stack.pop();
                          blockStack.pop();
                   }
      } catch (ParseException e) {
          errorSkipBlock();
      }
      break;
    case IF:
      try {
        jj_consume_token(IF);
                  if (genCode)
                  {
                        stack.push(offset);
                        blockStack.push("block"+(symbolCounter));
                        symbolTable.add(new Symbol("block"+symbolCounter++,"block"+nestingLevel,++nestingLevel,-1,-1,-1));
                        offset = 0;
                  }
        test();
                  if (genCode)
                  {
                    nextQuad = eQuadList.size();
                        o = stack.pop();
                        eQuadList.add(new ExtendedQuad("jeqz", o.toString(), null, null, -1));
                        stack.push(nextQuad);
                  }
        statement();
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case ELSE:
          jj_consume_token(ELSE);
                  if (genCode)
                  {
                    nextQuad = eQuadList.size();
                        eQuadList.add(new ExtendedQuad("jump", null, null, null, -1));
                        o = stack.pop();
                        eQuadList.get((int) o).setResult(new Field(""+(nextQuad+1), -1, -1, false));
                        stack.push(nextQuad);
                  }
          statement();
          break;
        default:
          jj_la1[3] = jj_gen;
          ;
        }
                  if (genCode)
                  {
                    o = stack.pop();
                    eQuadList.get((int) o).setResult(new Field(eQuadList.size()+"", -1, -1, false));
                    nestingLevel--;
                        offset = (int) stack.pop();
                        blockStack.pop();
                  }
      } catch (ParseException e) {
          errorSkipBlock();
      }
      break;
    case MINUS:
    case NOT:
    case LPAREN:
    case BREAK:
    case CONT:
    case RET:
    case INT:
    case ID:
      try {
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case MINUS:
        case NOT:
        case LPAREN:
        case INT:
        case ID:
          expression();
          jj_consume_token(SEMI);
          //clean off leftover stack
          if (genCode)
          {
            Object l = stack.pop();
          }
          break;
        case BREAK:
          jj_consume_token(BREAK);
          jj_consume_token(SEMI);
          if (genCode)
          {
            Object nq = breakStack.pop();
            breakStack.push(eQuadList.size());
            eQuadList.add(new ExtendedQuad("jump",null,null,nq.toString(),-1));
          }
          break;
        case RET:
          jj_consume_token(RET);
          expression();
          jj_consume_token(SEMI);
          if (genCode)
          {
            String a = stack.pop().toString();
            eQuadList.add(new ExtendedQuad("RTS", a, null, null, -1));
          }
          break;
        case CONT:
          jj_consume_token(CONT);
          jj_consume_token(SEMI);
          if (genCode)
          {
            Object loc = contStack.pop();
            contStack.push(loc);
            eQuadList.add(new ExtendedQuad("jump", null, null, loc.toString(), -1));
          }
          break;
        default:
          jj_la1[4] = jj_gen;
          jj_consume_token(-1);
          throw new ParseException();
        }
      } catch (ParseException e) {
        errorSkipTo(SEMI);
      }
      break;
    case SEMI:
      jj_consume_token(SEMI);
      break;
    default:
      jj_la1[5] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
  }

  static final public void compound_statement() throws ParseException {
    jj_consume_token(LBRACE);
          if (genCode)
          {
                //When a new left brace in encountered we want to increase the block number and nesting level
                //since this symbolizes the beginning of a new block
                //stack.push(offset);
                //blockStack.push("block"+(symbolCounter));
                //symbolTable.add(new Symbol("block"+symbolCounter++,"block"+nestingLevel,++nestingLevel,-1,-1,-1));
                //offset = 0;
          }
    statement_list();
    jj_consume_token(RBRACE);
          if (genCode)
          {
                //When a right brace is encountered, decrease the nesting level
                //nestingLevel--;
                //offset = (int) stack.pop();
                //blockStack.pop();
          }
  }

  static final public void statement_list() throws ParseException {
    statement();
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case MINUS:
    case NOT:
    case LBRACE:
    case LPAREN:
    case SEMI:
    case BREAK:
    case CONT:
    case IF:
    case RET:
    case WHILE:
    case INT:
    case ID:
      statement_list();
      break;
    default:
      jj_la1[6] = jj_gen;
      ;
    }
  }

  static final public void test() throws ParseException {
    try {
      jj_consume_token(LPAREN);
      expression();
      jj_consume_token(RPAREN);
    } catch (ParseException e) {
        errorSkipBlock();
    }
  }

  static final public void expression() throws ParseException {
    if (jj_2_1(2)) {
      jj_consume_token(ID);
          if (genCode)
          {
            //Push the token twice in order to handle multiple assignment statements. Eg: a=b=c=d;
                stack.push(getToken(0).image);
                stack.push(getToken(0).image);
                boolean add = true;
                Object block = blockStack.peek();
                for (Symbol s : symbolTable)
                {
                  if (s.getName().equals(getToken(0).image) && s.getBlock().equals(block.toString()))
                  {
                    //If the symbol table already contains this symbol in the same block, do not re-add it.
                    add = false;
                    break;
                  }
                }
                if (add)
                {
                  symbolTable.add(new Symbol(getToken(0).image, block.toString(), nestingLevel, offset*4, -1, -1));
                  offset++;
                }
          }
      jj_consume_token(EQ);
      expression();
          if (genCode)
          {
            Object exp = stack.pop();
                Object des = stack.pop();
                eQuadList.add(new ExtendedQuad("copy", exp.toString(), null, des.toString(), -1));
          }
    } else {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case MINUS:
      case NOT:
      case LPAREN:
      case INT:
      case ID:
        fn_call();
        break;
      default:
        jj_la1[7] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
    }
  }

  static final public void fn_call() throws ParseException {
  Token id, fn;
    condition();
          fn = getToken(0);
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case LPAREN:
      jj_consume_token(LPAREN);
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case ID:
        id = jj_consume_token(ID);
          if (genCode)
          {
                  for (Symbol s : symbolTable)
                  {
                        if (id.image.equals(s.getName()))
                        {
                          eQuadList.add(new ExtendedQuad("push", id.image, null, "stack", -1));
                          {if (true) return;}
                        }
                  }
          }
        break;
      default:
        jj_la1[8] = jj_gen;
        ;
      }
      jj_consume_token(RPAREN);
          if (genCode)
          {
                  for (Symbol s : symbolTable)
                  {
                        if (fn.image.equals(s.getName()))
                        {
                          eQuadList.add(new ExtendedQuad("jsr", null, null, s.getLocations()+"", -1));
                        }
                  }
          }
      break;
    default:
      jj_la1[9] = jj_gen;
      ;
    }
  }

  static final public void condition() throws ParseException {
    disjunction();
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case QMARK:
      jj_consume_token(QMARK);
          if (genCode)
          {
                  Object o = stack.pop();
                  int nq1 = eQuadList.size();
                  eQuadList.add(new ExtendedQuad("jeqz", o.toString(), null, null, -1));
                  stack.push(nq1);
          }
      expression();
          if (genCode)
          {
                  Object expr = stack.pop();
                  Object nq = stack.pop();
                  Object var = stack.pop();
                  eQuadList.add(new ExtendedQuad("copy", expr.toString(), null, var.toString(), -1));
                  stack.push(var);
                  stack.push(nq);
          }
      jj_consume_token(COLON);
          if (genCode)
          {
                  int nq2 = eQuadList.size();
                  eQuadList.add(new ExtendedQuad("jump", null, null, null, -1));
                  Object loc = stack.pop();
                  eQuadList.get((int) loc).setResult(new Field(eQuadList.size() + "", -1, -1, false));
                  stack.push(nq2);
          }
      condition();
          if (genCode)
          {
            Object b = stack.pop();
            Object a = stack.pop();
            Object c = stack.pop();
            eQuadList.get((int) a).setResult(new Field((eQuadList.size()+1)+"",-1,-1,false));
            stack.push(c);
            stack.push(b);
          }
      break;
    default:
      jj_la1[10] = jj_gen;
      ;
    }
  }

  static final public void disjunction() throws ParseException {
    conjunction();
    disjunctionP();
  }

  static final public void disjunctionP() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case OR:
      jj_consume_token(OR);
      conjunction();
          if (genCode)
          {
                  Object a = stack.pop();
                  Object b = stack.pop();
                  eQuadList.add(new ExtendedQuad("|", a.toString(), b.toString(), "temp"+tempCounter, -1));
                  stack.push("temp"+tempCounter++);
          }
      disjunctionP();
      break;
    default:
      jj_la1[11] = jj_gen;
      ;
    }
  }

  static final public void conjunction() throws ParseException {
    comparison();
    conjunctionP();
  }

  static final public void conjunctionP() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case AND:
      jj_consume_token(AND);
      comparison();
          if (genCode)
          {
            Object a = stack.pop();
            Object b = stack.pop();
            eQuadList.add(new ExtendedQuad("&", a.toString(), b.toString(), "temp"+tempCounter, -1));
            stack.push("temp"+tempCounter++);
          }
      conjunctionP();
      break;
    default:
      jj_la1[12] = jj_gen;
      ;
    }
  }

  static final public void comparison() throws ParseException {
    relation();
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case DEQ:
      jj_consume_token(DEQ);
      relation();
          if (genCode)
          {
            Object a = stack.pop();
                Object b = stack.pop();
                eQuadList.add(new ExtendedQuad("==", a.toString(), b.toString(), "temp"+tempCounter, -1));
                stack.push("temp"+tempCounter++);
          }
      break;
    default:
      jj_la1[13] = jj_gen;
      ;
    }
  }

  static final public void relation() throws ParseException {
    sum();
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case LT:
    case GT:
      rel_op();
      sum();
          if (genCode)
          {
            Object s1 = stack.pop();
            Object op = stack.pop();
            Object s2 = stack.pop();
            eQuadList.add(new ExtendedQuad(op.toString(), s2.toString(), s1.toString(), "temp"+tempCounter, -1));
            stack.push("temp"+tempCounter++);
          }
      break;
    default:
      jj_la1[14] = jj_gen;
      ;
    }
  }

  static final public void sum() throws ParseException {
    term();
    label_3:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case PLUS:
      case MINUS:
        ;
        break;
      default:
        jj_la1[15] = jj_gen;
        break label_3;
      }
      sumP();
    }
  }

  static final public void sumP() throws ParseException {
    add_op();
    term();
          if (genCode)
          {
                  Object op1 = stack.pop();
                  String operation = stack.pop().toString();
                  Object op2 = stack.pop();
                  eQuadList.add(new ExtendedQuad(operation, op1.toString(), op2.toString(), "temp"+tempCounter, -1));
                  stack.push("temp" + tempCounter++);
          }
  }

  static final public void term() throws ParseException {
    factor();
    label_4:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case MULTIPLY:
      case DIVIDE:
      case MOD:
        ;
        break;
      default:
        jj_la1[16] = jj_gen;
        break label_4;
      }
      termP();
    }
  }

  static final public void termP() throws ParseException {
    mul_op();
    factor();
          if (genCode)
          {
            Object op1 = stack.pop();
            String operation = stack.pop().toString();
            Object op2 = stack.pop();
            eQuadList.add(new ExtendedQuad(operation, op1.toString(), op2.toString(), "temp"+tempCounter, -1));
            stack.push("temp"+tempCounter++);
          }
  }

  static final public void factor() throws ParseException {
  Object op = null;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case MINUS:
    case NOT:
      unary_op();
      primary();
          if (genCode)
          {
                  Object p = stack.pop();
                  Object u = stack.pop();
                  if ("!".equals(u.toString()))
                  {
                    eQuadList.add(new ExtendedQuad("!", p.toString(), null, "temp"+tempCounter, -1));
                        stack.push("temp"+tempCounter++);
                  }
                  else if ("-".equals(u.toString()))
                  {
                    eQuadList.add(new ExtendedQuad("u-", p.toString(), null, "temp"+tempCounter, -1));
                    stack.push("temp"+tempCounter++);
                  }
          }
      break;
    case LPAREN:
    case INT:
    case ID:
      primary();
      break;
    default:
      jj_la1[17] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
  }

  static final public void primary() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case ID:
      jj_consume_token(ID);
          if (genCode) { stack.push(getToken(0).image); }
      break;
    case INT:
      jj_consume_token(INT);
          if (genCode) { stack.push(Integer.valueOf(getToken(0).image)); }
      break;
    case LPAREN:
      jj_consume_token(LPAREN);
      expression();
      jj_consume_token(RPAREN);
      break;
    default:
      jj_la1[18] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
  }

  static final public void rel_op() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case LT:
      jj_consume_token(LT);
          if (genCode) { stack.push("<"); }
      break;
    case GT:
      jj_consume_token(GT);
          if (genCode) { stack.push(">"); }
      break;
    default:
      jj_la1[19] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
  }

  static final public void mul_op() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case MULTIPLY:
      jj_consume_token(MULTIPLY);
          if (genCode) { stack.push("*"); }
      break;
    case DIVIDE:
      jj_consume_token(DIVIDE);
          if (genCode) { stack.push("/"); }
      break;
    case MOD:
      jj_consume_token(MOD);
          if (genCode) { stack.push("%"); }
      break;
    default:
      jj_la1[20] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
  }

  static final public void add_op() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case PLUS:
      jj_consume_token(PLUS);
          if (genCode) { stack.push("+"); }
      break;
    case MINUS:
      jj_consume_token(MINUS);
          if (genCode) { stack.push("-"); }
      break;
    default:
      jj_la1[21] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
  }

  static final public void unary_op() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case MINUS:
      jj_consume_token(MINUS);
          if (genCode) { stack.push("-"); }
      break;
    case NOT:
      jj_consume_token(NOT);
          if (genCode) { stack.push("!"); }
      break;
    default:
      jj_la1[22] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
  }

  static void errorSkipTo(int kind) throws ParseException {
  genCode = false;
  ParseException e = generateParseException();
  System.out.println(e.toString());
  System.out.println("Incorrect syntax detected. Ignoring statement.");
  Token t;
  do
  {
        t = getNextToken();
  } while  (t.kind != kind && t != null);
  }

  static void errorSkipBlock() throws ParseException {
  int cLevel = nestingLevel;
  genCode = false;
  ParseException e =  generateParseException();
  System.out.println(e.toString());
  System.out.println("Incorrect syntax detected. Encapsulated code block has been removed.");
  Token t;
  int kind = SEMI;
  do
  {
    t = getNextToken();
    if (t.kind == LBRACE)
    {
      kind = RBRACE;
    }
  } while(t.kind != kind && t != null);
  }

  static private boolean jj_2_1(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_1(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(0, xla); }
  }

  static private boolean jj_3_1() {
    if (jj_scan_token(ID)) return true;
    if (jj_scan_token(EQ)) return true;
    return false;
  }

  static private boolean jj_initialized_once = false;
  /** Generated Token Manager. */
  static public EG1TokenManager token_source;
  static SimpleCharStream jj_input_stream;
  /** Current token. */
  static public Token token;
  /** Next token. */
  static public Token jj_nt;
  static private int jj_ntk;
  static private Token jj_scanpos, jj_lastpos;
  static private int jj_la;
  static private int jj_gen;
  static final private int[] jj_la1 = new int[23];
  static private int[] jj_la1_0;
  static private int[] jj_la1_1;
  static {
      jj_la1_init_0();
      jj_la1_init_1();
   }
   private static void jj_la1_init_0() {
      jj_la1_0 = new int[] {0x40000000,0x3b80a440,0x0,0x4000000,0x13008440,0x3b80a440,0x3b80a440,0x8440,0x0,0x8000,0x80000,0x100000,0x200000,0x1000,0x60000,0x60,0x380,0x8440,0x8000,0x60000,0x380,0x60,0x440,};
   }
   private static void jj_la1_init_1() {
      jj_la1_1 = new int[] {0x0,0x6,0x4,0x0,0x6,0x6,0x6,0x6,0x4,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x6,0x6,0x0,0x0,0x0,0x0,};
   }
  static final private JJCalls[] jj_2_rtns = new JJCalls[1];
  static private boolean jj_rescan = false;
  static private int jj_gc = 0;

  /** Constructor with InputStream. */
  public EG1(java.io.InputStream stream) {
     this(stream, null);
  }
  /** Constructor with InputStream and supplied encoding */
  public EG1(java.io.InputStream stream, String encoding) {
    if (jj_initialized_once) {
      System.out.println("ERROR: Second call to constructor of static parser.  ");
      System.out.println("       You must either use ReInit() or set the JavaCC option STATIC to false");
      System.out.println("       during parser generation.");
      throw new Error();
    }
    jj_initialized_once = true;
    try { jj_input_stream = new SimpleCharStream(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
    token_source = new EG1TokenManager(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 23; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  /** Reinitialise. */
  static public void ReInit(java.io.InputStream stream) {
     ReInit(stream, null);
  }
  /** Reinitialise. */
  static public void ReInit(java.io.InputStream stream, String encoding) {
    try { jj_input_stream.ReInit(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
    token_source.ReInit(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 23; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  /** Constructor. */
  public EG1(java.io.Reader stream) {
    if (jj_initialized_once) {
      System.out.println("ERROR: Second call to constructor of static parser. ");
      System.out.println("       You must either use ReInit() or set the JavaCC option STATIC to false");
      System.out.println("       during parser generation.");
      throw new Error();
    }
    jj_initialized_once = true;
    jj_input_stream = new SimpleCharStream(stream, 1, 1);
    token_source = new EG1TokenManager(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 23; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  /** Reinitialise. */
  static public void ReInit(java.io.Reader stream) {
    jj_input_stream.ReInit(stream, 1, 1);
    token_source.ReInit(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 23; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  /** Constructor with generated Token Manager. */
  public EG1(EG1TokenManager tm) {
    if (jj_initialized_once) {
      System.out.println("ERROR: Second call to constructor of static parser. ");
      System.out.println("       You must either use ReInit() or set the JavaCC option STATIC to false");
      System.out.println("       during parser generation.");
      throw new Error();
    }
    jj_initialized_once = true;
    token_source = tm;
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 23; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  /** Reinitialise. */
  public void ReInit(EG1TokenManager tm) {
    token_source = tm;
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 23; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  static private Token jj_consume_token(int kind) throws ParseException {
    Token oldToken;
    if ((oldToken = token).next != null) token = token.next;
    else token = token.next = token_source.getNextToken();
    jj_ntk = -1;
    if (token.kind == kind) {
      jj_gen++;
      if (++jj_gc > 100) {
        jj_gc = 0;
        for (int i = 0; i < jj_2_rtns.length; i++) {
          JJCalls c = jj_2_rtns[i];
          while (c != null) {
            if (c.gen < jj_gen) c.first = null;
            c = c.next;
          }
        }
      }
      return token;
    }
    token = oldToken;
    jj_kind = kind;
    throw generateParseException();
  }

  static private final class LookaheadSuccess extends java.lang.Error { }
  static final private LookaheadSuccess jj_ls = new LookaheadSuccess();
  static private boolean jj_scan_token(int kind) {
    if (jj_scanpos == jj_lastpos) {
      jj_la--;
      if (jj_scanpos.next == null) {
        jj_lastpos = jj_scanpos = jj_scanpos.next = token_source.getNextToken();
      } else {
        jj_lastpos = jj_scanpos = jj_scanpos.next;
      }
    } else {
      jj_scanpos = jj_scanpos.next;
    }
    if (jj_rescan) {
      int i = 0; Token tok = token;
      while (tok != null && tok != jj_scanpos) { i++; tok = tok.next; }
      if (tok != null) jj_add_error_token(kind, i);
    }
    if (jj_scanpos.kind != kind) return true;
    if (jj_la == 0 && jj_scanpos == jj_lastpos) throw jj_ls;
    return false;
  }


/** Get the next Token. */
  static final public Token getNextToken() {
    if (token.next != null) token = token.next;
    else token = token.next = token_source.getNextToken();
    jj_ntk = -1;
    jj_gen++;
    return token;
  }

/** Get the specific Token. */
  static final public Token getToken(int index) {
    Token t = token;
    for (int i = 0; i < index; i++) {
      if (t.next != null) t = t.next;
      else t = t.next = token_source.getNextToken();
    }
    return t;
  }

  static private int jj_ntk() {
    if ((jj_nt=token.next) == null)
      return (jj_ntk = (token.next=token_source.getNextToken()).kind);
    else
      return (jj_ntk = jj_nt.kind);
  }

  static private java.util.List<int[]> jj_expentries = new java.util.ArrayList<int[]>();
  static private int[] jj_expentry;
  static private int jj_kind = -1;
  static private int[] jj_lasttokens = new int[100];
  static private int jj_endpos;

  static private void jj_add_error_token(int kind, int pos) {
    if (pos >= 100) return;
    if (pos == jj_endpos + 1) {
      jj_lasttokens[jj_endpos++] = kind;
    } else if (jj_endpos != 0) {
      jj_expentry = new int[jj_endpos];
      for (int i = 0; i < jj_endpos; i++) {
        jj_expentry[i] = jj_lasttokens[i];
      }
      jj_entries_loop: for (java.util.Iterator<?> it = jj_expentries.iterator(); it.hasNext();) {
        int[] oldentry = (int[])(it.next());
        if (oldentry.length == jj_expentry.length) {
          for (int i = 0; i < jj_expentry.length; i++) {
            if (oldentry[i] != jj_expentry[i]) {
              continue jj_entries_loop;
            }
          }
          jj_expentries.add(jj_expentry);
          break jj_entries_loop;
        }
      }
      if (pos != 0) jj_lasttokens[(jj_endpos = pos) - 1] = kind;
    }
  }

  /** Generate ParseException. */
  static public ParseException generateParseException() {
    jj_expentries.clear();
    boolean[] la1tokens = new boolean[35];
    if (jj_kind >= 0) {
      la1tokens[jj_kind] = true;
      jj_kind = -1;
    }
    for (int i = 0; i < 23; i++) {
      if (jj_la1[i] == jj_gen) {
        for (int j = 0; j < 32; j++) {
          if ((jj_la1_0[i] & (1<<j)) != 0) {
            la1tokens[j] = true;
          }
          if ((jj_la1_1[i] & (1<<j)) != 0) {
            la1tokens[32+j] = true;
          }
        }
      }
    }
    for (int i = 0; i < 35; i++) {
      if (la1tokens[i]) {
        jj_expentry = new int[1];
        jj_expentry[0] = i;
        jj_expentries.add(jj_expentry);
      }
    }
    jj_endpos = 0;
    jj_rescan_token();
    jj_add_error_token(0, 0);
    int[][] exptokseq = new int[jj_expentries.size()][];
    for (int i = 0; i < jj_expentries.size(); i++) {
      exptokseq[i] = jj_expentries.get(i);
    }
    return new ParseException(token, exptokseq, tokenImage);
  }

  /** Enable tracing. */
  static final public void enable_tracing() {
  }

  /** Disable tracing. */
  static final public void disable_tracing() {
  }

  static private void jj_rescan_token() {
    jj_rescan = true;
    for (int i = 0; i < 1; i++) {
    try {
      JJCalls p = jj_2_rtns[i];
      do {
        if (p.gen > jj_gen) {
          jj_la = p.arg; jj_lastpos = jj_scanpos = p.first;
          switch (i) {
            case 0: jj_3_1(); break;
          }
        }
        p = p.next;
      } while (p != null);
      } catch(LookaheadSuccess ls) { }
    }
    jj_rescan = false;
  }

  static private void jj_save(int index, int xla) {
    JJCalls p = jj_2_rtns[index];
    while (p.gen > jj_gen) {
      if (p.next == null) { p = p.next = new JJCalls(); break; }
      p = p.next;
    }
    p.gen = jj_gen + xla - jj_la; p.first = token; p.arg = xla;
  }

  static final class JJCalls {
    int gen;
    Token first;
    int arg;
    JJCalls next;
  }

}
