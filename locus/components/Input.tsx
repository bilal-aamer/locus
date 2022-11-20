import { TextField } from '@mui/material'
import { forwardRef } from 'react'

interface InputProps {
  size?: 'small' | 'medium'
  label?: string
  name: string
  placeholder?: string
  helperText?: string
  width?: string | number
  variant?: 'standard' | 'outlined' | 'filled'
  onChange?: any;
  value?: string;
  labelStyles?: any;
  style?:any;
  multiline?:boolean;
  rows?: number;
  containerStyle?: any
}

const Input = forwardRef((props: InputProps, ref) => (
    <div style={{ ...props.containerStyle, minWidth: props.width ?? 'auto' }}>
      {props.label !== undefined && <label className='m-3' htmlFor={props.name} style={props.labelStyles} > {props.label} </label> }
      <TextField inputRef={ref} style={props.style} multiline={props.multiline} rows={props.rows} size={props.size ?? "medium"} value={props.value} onChange={props.onChange} helperText={props.helperText ?? ''} InputLabelProps={{ shrink: false }} className='flex mx-3 mt-2 mb-5 ' placeholder={props.placeholder ?? ''} id={props.name} variant= {props.variant ?? 'outlined'} />
    </div>
))

export default Input
