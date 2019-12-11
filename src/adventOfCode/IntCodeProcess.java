package adventOfCode;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class IntCodeProcess {
    private Map<Integer, Long> numbers;
    private int position = 0;
    private int relativeBase = 0;
    private Deque<Long> input = new ArrayDeque<>();
    private Deque<Long> output = new ArrayDeque<>();

    IntCodeProcess(List<Long> numbers) {
        this.numbers = IntStream.range(0, numbers.size()).boxed()
                .collect(Collectors.toMap(Function.identity(), numbers::get));
    }

    IntCodeProcess(List<Long> numbers, int phase) {
        this(numbers);
        updateInput(phase);
    }

    private void updateInput(long...signal) {
        Arrays.stream(signal).forEach(input::addLast);
    }

    public boolean hasOutput() {
        return !output.isEmpty();
    }

    public Long getOutput() {
        return output.pollFirst();
    }

    public void run(long...signal) {
        updateInput(signal);
        while (!numbers.get(position).equals(99L)) {
            int[] params = getParams(numbers.get(position));
            switch (params[0]) {
                case 1:
                    numbers.put(getIndex(3, params), getValue(1, params) + getValue(2, params));
                    position += 4;
                    break;
                case 2:
                    numbers.put(getIndex(3, params), getValue(1, params) * getValue(2, params));
                    position += 4;
                    break;
                case 3:
                    if (input.peekFirst() == null) {
                        return;
                    } else {
                        numbers.put(getIndex(1, params), input.pollFirst());
                        position += 2;
                        break;
                    }
                case 4:
                    output.addLast(getValue(1, params));
                    position += 2;
                    break;
                case 5:
                    position = getValue(1, params) != 0L ? getValue(2, params).intValue() : position + 3;
                    break;
                case 6:
                    position = getValue(1, params) == 0L ? getValue(2, params).intValue() : position + 3;
                    break;
                case 7:
                    numbers.put(getIndex(3, params), getValue(1, params) < getValue(2, params) ? 1L : 0L);
                    position += 4;
                    break;
                case 8:
                    numbers.put(getIndex(3, params), getValue(1, params).equals(getValue(2, params)) ? 1L : 0L);
                    position += 4;
                    break;
                case 9:
                    relativeBase += getValue(1, params);
                    position += 2;
                    break;
                default:
                    return;
            }
        }
    }

    private int[] getParams(long code) {
        int command = (int) code;
        int[] params = new int[4];
        params[0] = command % 100;
        params[1] = command / 100 % 10;
        params[2] = command / 1000 % 10;
        params[3] = command / 10000;
        return params;
    }

    private int getIndex(int idx, int[] params) {
        switch(params[idx]) {
            case 0:
                return numbers.get(position + idx).intValue();
            case 1:
                return position + idx;
            case 2:
                return relativeBase + numbers.get(position + idx).intValue();
            default:
                return -1;
        }
    }

    private Long getValue(int idx, int[] params) {
        int index = getIndex(idx, params);
        return numbers.getOrDefault(index, 0L);
    }
}
