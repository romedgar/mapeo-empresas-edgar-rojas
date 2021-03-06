package ittepic.edu.ladm_u3_p1_edgar_gerardo_rojas_medina_mapeo_empresas.ui.notifications

import android.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import ittepic.edu.ladm_u3_p1_edgar_gerardo_rojas_medina_mapeo_empresas.Area
import ittepic.edu.ladm_u3_p1_edgar_gerardo_rojas_medina_mapeo_empresas.Subdepartamento
import ittepic.edu.ladm_u3_p1_edgar_gerardo_rojas_medina_mapeo_empresas.databinding.FragmentNotificationsBinding
import java.util.ArrayList

class NotificationsFragment : Fragment() {

    private var _binding: FragmentNotificationsBinding? = null
    var listaIDs = ArrayList<String>()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val notificationsViewModel =
            ViewModelProvider(this).get(NotificationsViewModel::class.java)

        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        notificationsViewModel.text.observe(viewLifecycleOwner) {
        }

        binding.buscar.setOnClickListener {
            buscar(binding.descripcion.text.toString(),binding.division.text.toString())
        }
        binding.insertar.setOnClickListener {
            var depto = Subdepartamento(this.requireContext())
            depto.idEdificio = binding.edifcio.text.toString()
            depto.piso = binding.piso.text.toString()
            depto.idArea = binding.idarea.text.toString().toInt()

            val resultado = depto.insertar()
            if(resultado){
                Toast.makeText(this.requireContext(),"SE INSERTO CON EXITO", Toast.LENGTH_LONG)
                    .show()
                binding.edifcio.setText("")
                binding.piso.setText("")
                binding.idarea.setText("")
            }else{
                AlertDialog.Builder(this.requireContext())
                    .setTitle("ERROR")
                    .setMessage("NO SE PUDO INSERTAR")
                    .show()
            }
        }
        return root
    }

    fun buscar(descripcion : String, division: String){
        var descripcion = descripcion
        var division = division

        if (descripcion.equals("") and division.equals("")){
            AlertDialog.Builder(this.requireContext())
                .setTitle("Aviso")
                .setMessage("Necesitas Introducir una Descripci??n o Divisi??n")
                .setNeutralButton("Cerrar"){d,i->}
                .show()
        }
        if (!descripcion.equals("") and !division.equals("")){
            //Aqu?? se genera la consulta doble
            var listaAreas = Area(this.requireContext()).mostrarCombinada(descripcion,division)
            var descripcionAreas = ArrayList<String>()

            listaIDs.clear()
            (0..listaAreas.size-1).forEach{
                val ar = listaAreas.get(it)
                descripcionAreas.add(ar.descripcion)
                listaIDs.add(ar.idArea.toString())
            }

            binding.lista.adapter = ArrayAdapter<String>(this.requireContext(), R.layout.simple_list_item_1,descripcionAreas)
            binding.lista.setOnItemClickListener { adapterView, view, indice, l ->
                val idAereaLista = listaIDs.get(indice)
                val area = Area(this.requireContext()).mostrarUno(idAereaLista.toInt())

                AlertDialog.Builder(this.requireContext())
                    .setTitle("Informaci??n")
                    .setMessage("Area: ${area.descripcion},\nDivisi??n: ${area.division}")
                    .setPositiveButton("Seleccionar"){d,i->
                        binding.idarea.setText(area.idArea.toString())
                    }
                    .setNeutralButton("Cerrar"){d,i->}
                    .show()
            }
        }
        if (!descripcion.equals("") and division.equals("")){
            //Aqu?? se genera la consulta por descripcion
            var listaAreas = Area(this.requireContext()).mostrarDescrip(descripcion)
            var descripcionAreas = ArrayList<String>()

            listaIDs.clear()
            (0..listaAreas.size-1).forEach{
                val ar = listaAreas.get(it)
                descripcionAreas.add(ar.descripcion)
                listaIDs.add(ar.idArea.toString())
            }

            binding.lista.adapter = ArrayAdapter<String>(this.requireContext(), R.layout.simple_list_item_1,descripcionAreas)
            binding.lista.setOnItemClickListener { adapterView, view, indice, l ->
                val idAereaLista = listaIDs.get(indice)
                val area = Area(this.requireContext()).mostrarUno(idAereaLista.toInt())

                AlertDialog.Builder(this.requireContext())
                    .setTitle("Informaci??n")
                    .setMessage("Area: ${area.descripcion},\nDivisi??n: ${area.division}")
                    .setPositiveButton("Seleccionar"){d,i->
                        binding.idarea.setText(area.idArea.toString())
                    }
                    .setNeutralButton("Cerrar"){d,i->}
                    .show()
            }
        }
        if (descripcion.equals("") and !division.equals("")){
            //Aqu?? se genera la consulta por divisi??n
            var listaAreas = Area(this.requireContext()).mostrarDiv(division)
            var descripcionAreas = ArrayList<String>()

            listaIDs.clear()
            (0..listaAreas.size-1).forEach{
                val ar = listaAreas.get(it)
                descripcionAreas.add(ar.descripcion)
                listaIDs.add(ar.idArea.toString())
            }

            binding.lista.adapter = ArrayAdapter<String>(this.requireContext(), R.layout.simple_list_item_1,descripcionAreas)
            binding.lista.setOnItemClickListener { adapterView, view, indice, l ->
                val idAereaLista = listaIDs.get(indice)
                val area = Area(this.requireContext()).mostrarUno(idAereaLista.toInt())

                AlertDialog.Builder(this.requireContext())
                    .setTitle("Informaci??n")
                    .setMessage("Area: ${area.descripcion},\nDivisi??n: ${area.division}")
                    .setPositiveButton("Seleccionar"){d,i->
                        binding.idarea.setText(area.idArea.toString())
                    }
                    .setNeutralButton("Cerrar"){d,i->}
                    .show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}