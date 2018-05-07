package com.example.lucas.testebd

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.CheckBox
import android.widget.TextView

class Aluno2Adapter(private val context: Context, private val dataSource: ArrayList<Aluno>): BaseAdapter()
{
    private val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    var listaAluno2= ArrayList<Aluno2>()

    override fun getCount(): Int
    {
        return dataSource.size
    }
    override fun getItem(position: Int): Any
    {
        return dataSource[position]
    }
    override fun getItemId(position: Int): Long
    {
        return position.toLong()
    }
    fun getTamListaAusentes(): Int
    {
        return listaAluno2.size
    }
    fun getListaAusentes(): ArrayList<Aluno2>
    {
        return listaAluno2
    }
    override fun getView(position: Int, convertView: View, parent: ViewGroup): View
    {
        var rva: RowViewAux
        val rowView = inflater.inflate(R.layout.list_aluno2, parent, false)
        val aluno= this.dataSource.get(position)

            val TextViewId = rowView.findViewById(R.id.aluno2_list_id) as TextView
            val TextViewNome = rowView.findViewById(R.id.aluno2_list_nome) as TextView
            val checkBox= rowView.findViewById(R.id.checkBoxAluno2) as CheckBox

            TextViewId.text = aluno.id
            TextViewNome.text = aluno.nome
            checkBox.isChecked= false
            listaAluno2.add(Aluno2(aluno.id, aluno.nome, aluno.matricula, false))

        return rowView

        /*val rowView = inflater.inflate(R.layout.list_aluno2, parent, false)

        val TextViewNome = rowView.findViewById(R.id.aluno2_list_nome) as TextView
        val TextViewMatricula = rowView.findViewById(R.id.aluno2_list_matricula) as TextView
        val checkBox= rowView.findViewById(R.id.checkBoxAluno2) as CheckBox



        val aluno = getItem(position) as Aluno
        TextViewNome.text = aluno.nome
        TextViewMatricula.text = aluno.matricula

        return rowView*/
    }
    class RowViewAux
    {
        companion object
        {
            lateinit var tv: TextView
            lateinit var cb: CheckBox
        }
    }

}