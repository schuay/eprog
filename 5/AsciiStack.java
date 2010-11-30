public class AsciiStack {
    int inc, capacity, size;
    AsciiImage stack[];

    public AsciiStack(int increment) {
        capacity = inc = increment;
        size = 0;
        stack = new AsciiImage[increment];
    }
    public int capacity() {
        return capacity;
    }
    public boolean empty() {
        return (size == 0);
    }
    public AsciiImage pop() {
        AsciiImage img = peek();
        if (img == null) {
            return img;
        }
        /* if we have enough space, reduce size of stack */
        if (--size <= capacity - inc - 1) {
            capacity -= inc;
            resize();
        }
        return img;
    }
    public AsciiImage peek() {
        if (empty()) {
            return null;
        }
        return stack[size - 1];
    }
    private void resize() {
        /* construct array with new capacity but identical contents */
        AsciiImage newstack[] = new AsciiImage[capacity];
        System.arraycopy(stack, 0, newstack, 0, Math.min(stack.length, newstack.length));
        stack = newstack;
    }
    public void push(AsciiImage img) {
        /* expand capacity if needed */
        if (++size > capacity) {
            capacity += inc;
            resize();
        }
        stack[size - 1] = img;
    }
    public int size() {
        return size;
    }
}
