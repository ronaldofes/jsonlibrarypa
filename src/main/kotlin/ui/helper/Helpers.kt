import java.awt.*
import java.awt.event.*
import javax.swing.*


fun main() {
    Editor().open()
}

class Editor {

    private val inscritosWidgets = mutableMapOf<String, Component>()
    private val cursosWidgets = mutableMapOf<String, Component>()

    val frame = JFrame("Josue - JSON Object Editor").apply {
        defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        layout = GridLayout(0, 2)
        size = Dimension(800, 1000)

        val left = JPanel()
        left.layout = GridLayout()
        val scrollPane = JScrollPane(testPanel()).apply {
            horizontalScrollBarPolicy = JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS
            verticalScrollBarPolicy = JScrollPane.VERTICAL_SCROLLBAR_ALWAYS
        }
        left.add(scrollPane)
        add(left)

        val right = JPanel()
        right.layout = GridLayout()
        val srcArea = JTextArea()
        srcArea.tabSize = 2
        srcArea.text = "TODO"
        right.add(srcArea)
        add(right)
    }

    fun open() {
        frame.isVisible = true
    }

    fun testPanel(): JPanel =
        JPanel(GridBagLayout()).apply {
            val gbc = GridBagConstraints().apply {
                fill = GridBagConstraints.BOTH
                weightx = 1.0
                gridwidth = GridBagConstraints.REMAINDER
            }
            gbc.weighty = 0.1

            add(testWidget("UC", "PA"), gbc)
            add(testWidget("ects", "6.0"), gbc)
            add(testWidget("exame", "N/A"), gbc)

            val inscritosWidgetsPanel = JPanel().apply {
                layout = BoxLayout(this, BoxLayout.Y_AXIS)
            }

            val inscritosPanel = JPanel().apply {
                layout = BoxLayout(this, BoxLayout.Y_AXIS)
                alignmentX = Component.LEFT_ALIGNMENT
                alignmentY = Component.TOP_ALIGNMENT
                add(JButton("Adicionar Inscrito").apply {
                    addActionListener {
                        val nome = JOptionPane.showInputDialog("Nome do Inscrito")
                        val numero = JOptionPane.showInputDialog("Número do Inscrito")
                        val internacional = JOptionPane.showConfirmDialog(null, "É Internacional?", "Internacional", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION
                        inscritosWidgetsPanel.add(inscritoWidget(nome, numero, internacional))
                        inscritosWidgetsPanel.revalidate()
                        inscritosWidgetsPanel.repaint()
                    }
                })
                add(JButton("Remover todos os Inscritos").apply {
                    addActionListener {
                        inscritosWidgetsPanel.removeAll()
                        inscritosWidgetsPanel.revalidate()
                        inscritosWidgetsPanel.repaint()
                    }
                })

                add(JButton("Remover Inscrito Pelo Nome").apply {
                    addActionListener {
                        val nome = JOptionPane.showInputDialog("Nome do Inscrito a remover")
                        val widgetToRemove = inscritosWidgets[nome]
                        if (widgetToRemove != null) {
                            inscritosWidgetsPanel.remove(widgetToRemove)
                            inscritosWidgets.remove(nome)
                            inscritosWidgetsPanel.revalidate()
                            inscritosWidgetsPanel.repaint()
                        } else {
                            JOptionPane.showMessageDialog(frame, "Inscrito não encontrado! Digite o nome exato do Inscrito")
                        }
                    }
                })

                add(inscritosWidgetsPanel)
            }

            val scrollPaneInscritos = JScrollPane(inscritosPanel).apply {
                horizontalScrollBarPolicy = JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED
                verticalScrollBarPolicy = JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED
            }
            gbc.weighty = 0.4
            add(scrollPaneInscritos, gbc)

            val cursosWidgetsPanel = JPanel().apply {
                layout = BoxLayout(this, BoxLayout.Y_AXIS)
            }

            val cursosPanel = JPanel().apply {
                layout = BoxLayout(this, BoxLayout.Y_AXIS)
                alignmentX = Component.LEFT_ALIGNMENT
                alignmentY = Component.TOP_ALIGNMENT
                add(JButton("Adicionar Curso").apply {
                    addActionListener {
                        val curso = JOptionPane.showInputDialog("Nome do Curso")
                        cursosWidgetsPanel.add(cursoWidget(curso))
                        cursosWidgetsPanel.revalidate()
                        cursosWidgetsPanel.repaint()
                    }
                })
                add(JButton("Remover todos os Cursos").apply {
                    addActionListener {
                        cursosWidgetsPanel.removeAll()
                        cursosWidgetsPanel.revalidate()
                        cursosWidgetsPanel.repaint()
                    }
                })

                add(JButton("Remover Curso Pelo Nome").apply {
                    addActionListener {
                        val curso = JOptionPane.showInputDialog("Nome do Curso a remover")
                        val widgetToRemove = cursosWidgets[curso]
                        if (widgetToRemove != null) {
                            cursosWidgetsPanel.remove(widgetToRemove)
                            cursosWidgets.remove(curso)
                            cursosWidgetsPanel.revalidate()
                            cursosWidgetsPanel.repaint()
                        } else {
                            JOptionPane.showMessageDialog(frame, "Curso não encontrado! Digite o nome exato do Curso")
                        }
                    }
                })
                add(cursosWidgetsPanel)
            }

            val scrollPaneCursos = JScrollPane(cursosPanel).apply {
                horizontalScrollBarPolicy = JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED
                verticalScrollBarPolicy = JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED
            }
            gbc.weighty = 0.4
            add(scrollPaneCursos, gbc)

//
//            // menu
//            addMouseListener(object : MouseAdapter() {
//                override fun mouseClicked(e: MouseEvent) {
//                    if (SwingUtilities.isRightMouseButton(e)) {
//                        val menu = JPopupMenu("Message")
//                        val add = JButton("add")
//                        add.addActionListener {
//                            val text = JOptionPane.showInputDialog("text")
//                            add(testWidget(text, "?"))
//                            menu.isVisible = false
//                            revalidate()
//                            frame.repaint()
//                        }
//                        val del = JButton("delete all")
//                        del.addActionListener {
//                            components.forEach {
//                                remove(it)
//                            }
//                            menu.isVisible = false
//                            revalidate()
//                            frame.repaint()
//                        }
//                        menu.add(add);
//                        menu.add(del)
//                        menu.show(this@apply, 100, 100);
//                    }
//                }
//            })
        }


    fun inscritoWidget(nome: String, numero: String, internacional: Boolean): JPanel =
        JPanel().apply {
            layout = BoxLayout(this, BoxLayout.X_AXIS)
            alignmentX = Component.LEFT_ALIGNMENT
            alignmentY = Component.TOP_ALIGNMENT

            add(JLabel("Nome: "))
            add(JTextField(nome))
            add(JLabel("Número: "))
            add(JTextField(numero))
            add(JLabel("Internacional: "))
            add(JCheckBox("", internacional))

            inscritosWidgets[nome] = this

        }

    fun cursoWidget(curso: String): JPanel =
        JPanel().apply {
            layout = BoxLayout(this, BoxLayout.X_AXIS)
            alignmentX = Component.LEFT_ALIGNMENT
            alignmentY = Component.TOP_ALIGNMENT

            add(JLabel("Curso: "))
            add(JTextField(curso))

            cursosWidgets[curso] = this
        }

    fun testWidget(key: String, value: String): JPanel =
        JPanel().apply {
            layout = BoxLayout(this, BoxLayout.X_AXIS)
            alignmentX = Component.LEFT_ALIGNMENT
            alignmentY = Component.TOP_ALIGNMENT

            add(JLabel(key))
            val text = JTextField(value)
            text.addFocusListener(object : FocusAdapter() {
                override fun focusLost(e: FocusEvent) {
                    println("perdeu foco: ${text.text}")
                }
            })
            add(text)
        }
}






