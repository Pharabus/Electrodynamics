package electrodynamics.circuit.components;

import electrodynamics.circuit.Component;
import electrodynamics.circuit.ComponentContainer;

public class NOR_Gate extends Component {
        
        public NOR_Gate()
        {
            this.pin_id_read = new int[3];
            this.pin_id_write = new int[1];
        }

        @Override
        public void onUpdate(ComponentContainer parent) {
            // TODO Auto-generated method stub
            boolean input_pin[] = parent.readPin(this.pin_id_read);
            boolean output_pin[] = new boolean [1];
            
            for(int i = 0; i < input_pin.length; i++)
                output_pin[0] &= !input_pin[i];
            
            parent.writePin(pin_id_write, output_pin);
        }

}
