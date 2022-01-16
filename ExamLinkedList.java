public class ExamLinkedList<E> {
        private static class Node<E> {

            private E data;
            private Node<E> next = null;

            public Node(E data, Node<E> next) {
                this.data = data;
                this.next = next;
            }

            public Node(E data) {
                this(data, null);
            }
        }


        private Node<E> head = null;
        private int size = 0;


        private void addFirst(E item) {

            head = new Node<E>(item, head);
            size++;
        }

        private void addAfter(Node<E> node, E item) {
            node.next = new Node<E>(item, node.next);
            size++;
        }

        private E removeFirst() {
            Node<E> temp = head;
            if (head != null) {
                head = head.next;
            }
            if (temp != null) {
                size--;
                return temp.data;
            } else {
                return null;
            }
        }

        private E removeAfter(Node<E> node) {
            Node<E> temp = node.next;
            if (temp != null) {
                node.next = temp.next;
                size--;
                return temp.data;
            } else {
                return null;
            }
        }


        private Node<E> getNode(int index) {
            Node<E> node = head;
            for (int i = 0; i < index && node != null; i++) {
                node = node.next;
            }
            return node;
        }


        public E get(int index) {
            if (index < 0 || index >= size) {
                throw new IndexOutOfBoundsException(Integer.toString(index));
            }
            Node<E> node = getNode(index);
            return node.data;
        }


        public E set(int index, E newValue) {
            if (index < 0 || index >= size) {
                throw new IndexOutOfBoundsException(Integer.toString(index));
            }
            Node<E> node = getNode(index);
            E result = node.data;
            node.data = newValue;
            return result;
        }

        public void add(int index, E item) {
            if (index < 0 || index > size) {
                throw new IndexOutOfBoundsException(Integer.toString(index));
            }
            if (index == 0) {
                addFirst(item);
            } else {
                Node<E> node = getNode(index - 1);
                addAfter(node, item);
            }
        }


        public void addLast(E item) {
            add(size, item);
        }


        public E remove(int index) {
            if (index < 0 || index >= size) {
                throw new IndexOutOfBoundsException(Integer.toString(index));
            }
            Node<E> removedNode = null;
            if (index == 0) {
                return removeFirst();
            } else {
                Node<E> node = getNode(index - 1);
                return removeAfter(node);
            }
        }

        int size() {
            return size;
        }


        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder("[");
            Node p = head;
            if (p != null) {
                while (p.next != null) {
                    sb.append(p.data.toString());
                    sb.append(" ==> ");
                    p = p.next;
                }
                sb.append(p.data.toString());
            }
            sb.append("]");
            return sb.toString();
        }

        public boolean removeItem(E item) {
            if (head == null) {
                return false;
            }
            Node<E> current = head;
            if (item.equals(current.data)) {
                removeFirst();
                return true;
            }
            while (current.next != null) {
                if (item.equals(current.next.data)) {
                    removeAfter(current);
                    return true;
                }
                current = current.next;
            }
            return false;
        }

        public void addBeforeItem(E bitem, E item) {

            if (head == null)
                return;
            if (head.data.equals(bitem)) {
                addFirst(item);
                return;

            }
            Node<E> prev = null;
            Node<E> cur = head;

            while (cur != null && !cur.data.equals(bitem)) {
                prev = cur;
                cur = cur.next;
            }

            if (cur != null)
                prev.next = new Node<E>(item, cur);
            size++;

        }

        public void addBeforeIndex(int index, E item) {
            if (index < 0) {
                throw new IndexOutOfBoundsException(Integer.toString(index));
            }
            if (index == 0) {
                head = new Node(item, head);
                size++;
            } else {
                int i = index;
                Node<E> current = head;
                while (current != null && --i > 0) {
                    current = current.next;
                }
                if (i == 0) {
                    current.next = new Node(item, current.next);
                    size++;
                } else {
                    throw new IndexOutOfBoundsException(Integer.toString(index));
                }
            }
        }

        //5-23-45-56
        //23-45-56-56-5
        // Q2.A
        public void createCopy(int index){
            E item = get(index);
            Node<E> node = getNode(index);
            addAfter(node, item);
        }

        // Q2.B
        public void lowestFirst(){
            E lowest = head.data;
            Node<E> current = head;
            int     indexOfLowest = 0;
            for (int i = 0; i < size(); i++){
                if ((int)lowest > (int)current.data){
                    lowest = current.data;
                    indexOfLowest = i;
                }
                current = current.next;
            }
            if (lowest == head.data)
                return;
            else {
                remove(indexOfLowest);
                addFirst(lowest);
            }
        }

        //Q2.C
        public int countGreaterThan(int target){
            Node<E> current = head;
            int     count = 0;
            int     sum = 0;
            while (current != null){
                if ((int)current.data >= target)
                    count++;
                if ((int)current.data > target)
                    sum += (int)current.data;
                current = current.next;
            }
            return sum;
        }

        public static void main(String args[]) {
            ExamLinkedList<Integer> sll = new ExamLinkedList<Integer>();

            sll.addLast(56);
            sll.addLast(45);
            sll.addLast(80);
            sll.addLast(10);
            sll.addLast(90);


            System.out.println(sll);
            sll.createCopy(1);
            System.out.println(sll);
            // Should be: [56 ==> 45 ==> 45 ==> 80 ==> 10 ==> 90]

            sll.lowestFirst();
            System.out.println(sll);
            // Should be: [10 ==> 56 ==> 45 ==> 45 ==> 80 ==> 90]

            System.out.println(sll.countGreaterThan(80));
            // Should be: 2

        }
}
