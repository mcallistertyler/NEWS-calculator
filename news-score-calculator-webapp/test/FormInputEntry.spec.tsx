import { expect, test, vi} from 'vitest';
import { userEvent } from '@vitest/browser/context'
import { render } from 'vitest-browser-react';
import FormInputEntry from "../src/client/components/form/FormInputEntry";
import {MeasurementType} from "../src/client/types/measurement";
import '../src/client/index.css';

test('renders form input entry', async () => {
    const { getByTestId }  = render(<FormInputEntry id={MeasurementType.TEMP} header={"Body temperature"} subHeader={"Degrees celcius"} onChange={(e) => console.log("test")}/>);
    await expect.element(getByTestId(MeasurementType.TEMP)).toBeInTheDocument();
});

test('test accessibility', async () => {
    const { getByTestId }  = render(<FormInputEntry id={MeasurementType.TEMP} header={"Body temperature"} subHeader={"Degrees celcius"} onChange={(e) => console.log("test")}/>);

    const inputElement = getByTestId(`${MeasurementType.TEMP}-input`);
    await expect.element(inputElement).toHaveAttribute('aria-describedby');
    await expect.element(inputElement).toHaveAttribute('aria-labelledby');

    await expect.element(inputElement).toHaveAttribute('aria-labelledby', `${MeasurementType.TEMP}-header`);
    await expect.element(inputElement).toHaveAttribute('aria-describedby', `${MeasurementType.TEMP}-subHeader`);

})

test('calls onchange when input value changes', async() => {
    const { getByTestId } = render(<FormInputEntry id={MeasurementType.TEMP} header={"Body temperature"} subHeader={"Degrees celcius"} onChange={(e) => console.log("test")}/>)
    const inputElement = getByTestId(`${MeasurementType.TEMP}-input`);
    await expect.element(inputElement).toBeInTheDocument();
    await userEvent.type(inputElement, "39");
    await expect.element(inputElement).toHaveValue(39);

})
