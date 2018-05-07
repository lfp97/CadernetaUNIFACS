package com.example.lucas.testebd

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.CheckBox
import android.widget.TextView

class Alunos_TurmasAdapter(private val context: Context, private val dataSource: ArrayList<Alunos_Turmas>) : BaseAdapter()
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
        val rowView = inflater.inflate(R.layout.list_alunos_turmas, parent, false)

        val TextViewIdAlu = rowView.findViewById(R.id.alunos_turmas_list_idaluno) as TextView
        val TextViewIdTur = rowView.findViewById(R.id.alunos_turmas_list_idturma) as TextView
        val checkBox= rowView.findViewById(R.id.checkBoxAlunos_turmas) as CheckBox

        val at = getItem(position) as Alunos_Turmas
        TextViewIdAlu.text = at.idAluno
        TextViewIdTur.text = at.idTurma

        return rowView
    }
}