import java.util.function.IntBinaryOperator;

class Operator {

    public static IntBinaryOperator binaryOperator = (x, y) -> {
        if (x > y) {
            return x;
        } else {
            return y;
        }
    };
}