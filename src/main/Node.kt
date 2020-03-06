package main

class Node(var key: Int,
           var left: Node? = null,
           var right: Node? = null) {

    fun search(targetKey: Int): Node? {
        return when {
            targetKey == key -> this
            targetKey > key -> right?.search(targetKey)
            else -> left?.search(targetKey)
        }
    }

    fun insert(newKey: Int) {
        when {
            newKey > key -> {
                if (right == null) {
                    right = Node(newKey)
                } else {
                    right?.insert(newKey)
                }
            }
            newKey < key -> {
                if (left == null) {
                    left = Node(newKey)
                } else {
                    left?.insert(newKey)
                }
            }
        }
    }

    fun visit(): Array<Int> {
        val a = left?.visit() ?: emptyArray()
        val b = right?.visit() ?: emptyArray()
        return a + arrayOf(key) + b
    }

    fun remove(toRemoveVal: Int) {
        when {
            toRemoveVal > key -> right?.remove(toRemoveVal, this)
            toRemoveVal < key -> left?.remove(toRemoveVal, this)
        }
    }

    private fun remove(toRemoveVal: Int, parent: Node) {
        when {
            toRemoveVal > key -> right?.remove(toRemoveVal, this)
            toRemoveVal < key -> left?.remove(toRemoveVal, this)
            else -> removeNode(toRemoveVal, parent)
        }
    }

    private fun removeNode(toRemoveVal: Int, parent: Node) {
        if (toRemoveVal == key) {
            when {
                this.left == null && this.right == null -> removeNoChildNode(parent)
                this.left != null && this.right != null -> removeTwoChildNode(this)
                else -> {
                    val child = if (left == null) right else left
                    removeSingleChildNode(child!!)
                }
            }
        }
    }

    private fun removeNoChildNode(parent: Node?) {
        if (parent == null) {
            throw IllegalStateException("Can not remove the root node without child nodes")
        }
        if (this == parent.left) {
            parent.left = null
        } else if (this == parent.right) {
            parent.right = null
        }
    }

    private fun removeSingleChildNode(child: Node) {
        key = child.key
        left = child.left
        right = child.right
    }

    private fun removeTwoChildNode(node: Node) {
        val leftChild = node.left!!
        leftChild.right?.let {
            val maxParent = findParentOfMaxChild(leftChild)
            maxParent.right?.let {
                node.key = it.key
                maxParent.right = null
            } ?: throw IllegalStateException("main.Node with max child must have the right child!")
        } ?: run {
            node.key = leftChild.key
            node.left = leftChild.left
        }
    }

    private fun findParentOfMaxChild(n: Node): Node {
        return n.right?.let { r -> r.right?.let { findParentOfMaxChild(r) } ?: n }
            ?: throw IllegalArgumentException("Right child must be non-null")
    }
}