package es.orcelis.orcelis.operations.cultivos;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import es.orcelis.orcelis.R;

public class OpcionesCultivosListDialogFragment extends BottomSheetDialogFragment {
    public static String TAG = "OpcionesCultivosListDialogFragment";
    private static final int COUNT_ITEMS = 2;
    private Listener mListener;

    public static OpcionesCultivosListDialogFragment newInstance( ) {
        final OpcionesCultivosListDialogFragment fragment = new OpcionesCultivosListDialogFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_opcionescultivos_list_dialog, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        final LinearLayout contenedor = (LinearLayout)view;

        final RecyclerView recyclerView = (RecyclerView) contenedor.findViewById(R.id.lista_opciones_inferior);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recyclerView.setAdapter(new OpcionesCultivosAdapter(COUNT_ITEMS));
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        final Fragment parent = getParentFragment();
        if (parent != null) {
            mListener = (Listener) parent;
        } else {
            mListener = (Listener) context;
        }
    }

    @Override
    public void onDetach() {
        mListener = null;
        super.onDetach();
    }

    public interface Listener {
        void onOpcionesCultivosClicked(int position);
    }

    private class ViewHolder extends RecyclerView.ViewHolder {

        final TextView text;
        final ImageView icono;
        final LinearLayout contenedor_fila;

        ViewHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.fragment_opcionescultivos_list_dialog_item, parent, false));
            text = (TextView) itemView.findViewById(R.id.text);
            icono = (ImageView) itemView.findViewById(R.id.icono_opcion_inferior);
            contenedor_fila = (LinearLayout)itemView.findViewById(R.id.contenedor_fila);
            contenedor_fila.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        mListener.onOpcionesCultivosClicked(getAdapterPosition());
                        dismiss();
                    }
                }
            });
        }

    }

    private class OpcionesCultivosAdapter extends RecyclerView.Adapter<ViewHolder> {

        private final int mItemCount;

        OpcionesCultivosAdapter(int itemCount) {
            mItemCount = itemCount;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()), parent);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            if(position == 0) {
                holder.icono.setImageDrawable(getResources().getDrawable(R.drawable.ic_description_black_48dp));
                holder.text.setText(getString(R.string.tv_inspecccion));
            }
            else{
                holder.icono.setImageDrawable(getResources().getDrawable(R.drawable.ic_my_location_black_48dp));
                holder.text.setText(getString(R.string.tv_cultivo));
            }

        }

        @Override
        public int getItemCount() {
            return mItemCount;
        }

    }

}
