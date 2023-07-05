import org.hyperskill.hstest.dynamic.DynamicTest;
import org.hyperskill.hstest.stage.StageTest;
import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testing.TestedProgram;

import java.util.Arrays;


public class TrafficLightTest extends StageTest {

  @DynamicTest(order = 2)
  CheckResult test_stubs() {
    TestedProgram pr = new TestedProgram();

    pr.start();

    pr.execute("5");
    pr.execute("3");

    String[] starts = new String[]{"1", "2", "3", "0"};
    String[] contain = new String[]{"add", "delete", "system", "quit"};

    for (int i = 0; i < starts.length - 1; i++) {
      String output = pr.execute(starts[i]).toLowerCase();
      String[] lines = output.split("[\r\n]+");

      if (lines.length != 6) {
        {
          return CheckResult.wrong("After any chosen option (except '0') there should be printed exactly 6 non-empty " +
                  "lines, containing simple one-line stub and menu");
        }
      }

      if (!lines[0].contains(contain[i])) {
        return CheckResult.wrong(String.format("If the user had chosen '%s' as an option, the first line of an output" +
                " (stub) should contain \"%s\" substring", starts[i], contain[i]));
      }
      GlobalTests.CheckMenu(Arrays.copyOfRange(lines, 1, 6), "Stub shown up.");

      if (pr.isFinished()) {
        return CheckResult.wrong("Option's selection should be looped");
      }
    }
    pr.execute("0");
    if (!pr.isFinished()) {
      return CheckResult.wrong("After user inputted '0' as a desired option, program should finish it's execution");
    }
    return CheckResult.correct();
  }
}