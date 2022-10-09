package com.liubuyao.utils.bluetoothUtils

import android.bluetooth.BluetoothDevice
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.liubuyao.utils.R

class BluetoothDeviceAdapter : RecyclerView.Adapter<BluetoothDeviceAdapter.MyViewHolder>() {

    private var datas: MutableList<BluetoothDevice> = mutableListOf()
    private var mOnClickListener: OnClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflate =
            LayoutInflater.from(parent.context).inflate(R.layout.item_bluetooth, parent, false)
        return MyViewHolder(inflate)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.tvName.text = datas[position].name ?: ""
        holder.btnConnect.setOnClickListener {
            mOnClickListener?.connectClick(datas[position])
        }
    }

    override fun getItemCount(): Int {
        return datas.size ?: 0

    }

    fun setData(data: List<BluetoothDevice>) {
        datas = data.toMutableList()
        notifyDataSetChanged()
    }

    fun addData(data: BluetoothDevice) {
        if (!datas.contains(data)) {
            datas.add(data)
            notifyDataSetChanged()
        }
    }

    fun getData(): List<BluetoothDevice> {
        return datas
    }

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvName = view.findViewById<TextView>(R.id.tvName)
        val btnConnect = view.findViewById<Button>(R.id.btnConnect)
    }

    interface OnClickListener {
        fun connectClick(device: BluetoothDevice)
    }

    fun setOnClickListener(onClickListener: OnClickListener) {
        mOnClickListener = onClickListener
    }
}