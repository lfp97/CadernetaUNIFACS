package com.example.lucas.testebd

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView

class AlunoAdapter(private val context: Context, private val dataSource: ArrayList<Aluno>) : BaseAdapter()
{
    private val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

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
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View
    {
        val rowView = inflater.inflate(R.layout.list_aluno, parent, false)

        val TextViewNome = rowView.findViewById(R.id.aluno_list_nome) as TextView
        val TextViewMatricula = rowView.findViewById(R.id.aluno_list_matricula) as TextView
        //val checkBox= rowView.findViewById(R.id.checkBoxAluno) as CheckBox

        val aluno = getItem(position) as Aluno
        TextViewNome.text = aluno.nome
        TextViewMatricula.text = aluno.matricula

        return rowView
    }
}