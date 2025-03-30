import React, { ChangeEvent } from 'react';
import Input from '../base/Input';

interface FormEntryProps {
    id: string;
    header: string;
    subHeader: string;
    required?: boolean;
    onChange: (id: string, value: string) => void;
}

function FormInputEntry({id, header, subHeader, required, onChange}: FormEntryProps) {

    const handleInputChange = (e: ChangeEvent<HTMLInputElement>) => {
        onChange(id, e.target.value);
    }

    return (
        <div data-testid={id} id={id}>
            <h3 id={`${id}-header`} className="font-semibold pb-[8px]">{header}</h3>
            <div id={`${id}-subheader`} className="small-text pb-[12px]">{subHeader}</div>
            <Input id={`${id}-input`} onChange={handleInputChange} required={required} type="number" aria-required={required} aria-describedby={`${id}-subHeader`} aria-labelledby={`${id}-header`} />
        </div>
    );
}

export default FormInputEntry;

