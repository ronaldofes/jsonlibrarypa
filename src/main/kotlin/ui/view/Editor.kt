package ui.view

import builder.JsonBuilder
import model.BeautyJson
import ui.model.Curso
import ui.model.Inscrito
import ui.model.Modelo
import java.awt.*
import java.awt.event.*
import javax.swing.*

class Editor {
    private val modelo = Modelo("PA", "6.0", "N/A")
    private val inscritosWidgets = mutableMapOf<String, Component>()
    private val cursosWidgets = mutableMapOf<String, Component>()
    private val builder = JsonBuilder()
    private val ordering = listOf("uc", "ects", "exame", "inscritos", "cursos" )
    private val beauty = BeautyJson(ordering)


    private val srcArea = JTextArea().apply {
        tabSize = 2
    }

    private val frame = JFrame("JSON Builder").apply {
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
        right.layout = BorderLayout()
        srcArea.text = ""

        val exportButton = JButton("Exportar para JSON").apply {
            addActionListener {
                val fileChooser = JFileChooser()
                val returnValue = fileChooser.showSaveDialog(null)
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    val selectedFile = fileChooser.selectedFile
                    val json = beauty.build(builder.buildJson(modelo))
                    selectedFile.writeText(json)
                }
            }

            maximumSize = Dimension(right.width, height/10)
        }

       val showJsonButton = JButton("Mostrar JSON").apply {
            addActionListener {
                val json = beauty.build(builder.buildJson(modelo))
                srcArea.text = json
            }
            maximumSize = Dimension(right.width, height/10)
        }

        right.add(JScrollPane(srcArea), BorderLayout.CENTER)
        right.add(exportButton, BorderLayout.PAGE_END)
        right.add(showJsonButton, BorderLayout.PAGE_START)
        add(right)
    }



    fun open() {
        frame.isVisible = true
    }

    private fun updateTextArea() {
        var text = "UC: ${modelo.uc}, ECTS: ${modelo.ects}, Exame: ${modelo.exame}\n"
        modelo.inscritos.forEach { inscrito ->
            text += "Inscrito: ${inscrito.nome}, Número: ${inscrito.numero}, Internacional: ${inscrito.internacional}\n"
        }
        modelo.cursos.forEach { curso ->
            text += "Curso: ${curso.nome}\n"
        }
        srcArea.text = text
    }

    private fun testPanel(): JPanel =
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
                        val nome = JOptionPane.showInputDialog("Nome do Inscrito") ?: return@addActionListener
                        val numero = JOptionPane.showInputDialog("Número do Inscrito") ?: return@addActionListener
                        val internacional = JOptionPane.showConfirmDialog(null, "É Internacional?", "Internacional", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION
                        val inscrito = Inscrito(nome, numero, internacional)
                        modelo.inscritos.add(inscrito)
                        inscritosWidgetsPanel.add(inscritoWidget(inscrito))
                        inscritosWidgetsPanel.revalidate()
                        inscritosWidgetsPanel.repaint()
                        updateTextArea()
                    }
                })

                add(JButton("Remover Inscrito Pelo Nome").apply {
                    addActionListener {
                        val nome = JOptionPane.showInputDialog("Nome do Inscrito a remover")
                        if (nome != null) {
                            val inscrito = modelo.inscritos.find { it.nome == nome }
                            if (inscrito != null) {
                                modelo.inscritos.remove(inscrito)
                                val widgetToRemove = inscritosWidgets[inscrito.nome]
                                if (widgetToRemove != null) {
                                    inscritosWidgetsPanel.remove(widgetToRemove)
                                    inscritosWidgets.remove(inscrito.nome)
                                    inscritosWidgetsPanel.revalidate()
                                    inscritosWidgetsPanel.repaint()
                                    updateTextArea()
                                }
                            } else {
                                JOptionPane.showMessageDialog(frame, "Inscrito não encontrado! Digite o nome exato do Inscrito")
                            }
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
                        val cursoNome = JOptionPane.showInputDialog("Nome do Curso")
                        if (cursoNome != null) {
                            val curso = Curso(cursoNome)
                            modelo.cursos.add(curso)
                            cursosWidgetsPanel.add(cursoWidget(curso))
                            cursosWidgetsPanel.revalidate()
                            cursosWidgetsPanel.repaint()
                            updateTextArea()
                        }
                    }
                })

                add(JButton("Remover Curso Pelo Nome").apply {
                    addActionListener {
                        val cursoNome = JOptionPane.showInputDialog("Nome do Curso a remover")
                        if (cursoNome != null) {
                            val cursoObj = modelo.cursos.find { it.nome == cursoNome }
                            if (cursoObj != null) {
                                modelo.cursos.remove(cursoObj)
                                val widgetToRemove = cursosWidgets[cursoObj.nome]
                                if (widgetToRemove != null) {
                                    cursosWidgetsPanel.remove(widgetToRemove)
                                    cursosWidgets.remove(cursoObj.nome)
                                    cursosWidgetsPanel.revalidate()
                                    cursosWidgetsPanel.repaint()
                                    updateTextArea()
                                }
                            } else {
                                JOptionPane.showMessageDialog(frame, "Curso não encontrado! Digite o nome exato do Curso")
                            }
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
        }


    private fun inscritoWidget(inscrito: Inscrito): JPanel =
        JPanel().apply {
            layout = BoxLayout(this, BoxLayout.X_AXIS)
            alignmentX = Component.LEFT_ALIGNMENT
            alignmentY = Component.TOP_ALIGNMENT

            add(JLabel("Nome: "))
            val nomeField = JTextField(inscrito.nome)
            nomeField.addFocusListener(object : FocusAdapter() {
                override fun focusLost(e: FocusEvent) {
                    val oldNome = inscrito.nome
                    inscrito.nome = nomeField.text
                    // Atualize a chave no mapa dos widgets.
                    inscritosWidgets.remove(oldNome)
                    inscritosWidgets[inscrito.nome] = this@apply
                    updateTextArea()
                }
            })
            add(nomeField)
            add(JLabel("Número: "))
            val numeroField = JTextField(inscrito.numero)
            numeroField.addFocusListener(object : FocusAdapter() {
                override fun focusLost(e: FocusEvent) {

                    inscrito.numero = numeroField.text
                    updateTextArea()
                }
            })
            add(numeroField)
            add(JLabel("Internacional: "))
            val internacionalCheckBox = JCheckBox("", inscrito.internacional)
            internacionalCheckBox.addItemListener {
                inscrito.internacional = internacionalCheckBox.isSelected
                updateTextArea()
            }
            add(internacionalCheckBox)

            inscritosWidgets[inscrito.nome] = this
        }

    private fun cursoWidget(curso: Curso): JPanel =
        JPanel().apply {
            layout = BoxLayout(this, BoxLayout.X_AXIS)
            alignmentX = Component.LEFT_ALIGNMENT
            alignmentY = Component.TOP_ALIGNMENT

            add(JLabel("Curso: "))
            val cursoField = JTextField(curso.nome)
            cursoField.addFocusListener(object : FocusAdapter() {
                override fun focusLost(e: FocusEvent) {
                    val oldNome = curso.nome
                    curso.nome = cursoField.text
                    cursosWidgets.remove(oldNome)
                    cursosWidgets[curso.nome] = this@apply
                    updateTextArea()
                }
            })
            add(cursoField)

            cursosWidgets[curso.nome] = this
        }



    private fun testWidget(key: String, value: String): JPanel =
        JPanel().apply {
            layout = BoxLayout(this, BoxLayout.X_AXIS)
            alignmentX = Component.LEFT_ALIGNMENT
            alignmentY = Component.TOP_ALIGNMENT

            add(JLabel(key))
            val text = JTextField(value)
            text.addFocusListener(object : FocusAdapter() {
                override fun focusLost(e: FocusEvent) {
                    when (key) {
                        "UC" -> modelo.uc = text.text
                        "ects" -> modelo.ects = text.text
                        "exame" -> modelo.exame = text.text
                    }
                    updateTextArea()
                }
            })
            add(text)
        }
}






