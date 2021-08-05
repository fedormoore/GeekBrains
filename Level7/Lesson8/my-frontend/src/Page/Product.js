import React, {useEffect} from "react";
import {useDispatch} from "react-redux";

import Pagination from '@material-ui/lab/Pagination';

import Table from "@material-ui/core/Table";
import TableHead from "@material-ui/core/TableHead";
import TableBody from "@material-ui/core/TableBody";

import TableRow from "@material-ui/core/TableRow";
import TableCell from "@material-ui/core/TableCell";

import Grid from '@material-ui/core/Grid';

import Button from "@material-ui/core/Button";

import {addCart, fTest, getCryptocurrency} from "../util/network";
import TextField from "@material-ui/core/TextField";

function Product(props) {

    const dispatch = useDispatch()

    const [products, setProducts] = React.useState([]);
    const [pagination, setPagination] = React.useState(0);

    const [values, setValues] = React.useState([]);

    useEffect(() => {
        loadCryptocurrency({
            p: '0'
        });
        // eslint-disable-next-line
    }, []);

    const loadCryptocurrency = (page) => {
        dispatch(getCryptocurrency(page))
            .then((response) => {
                setProducts(response.content);
                setPagination(response.size)
            })
            .catch((error) => {
                console.log(error);
            });
    };

    const handleChange = (event, value) => {
        loadCryptocurrency({
            p: value
        })
    };

    function clickFilter() {
        loadCryptocurrency({
            title: values.title,
            min_price: values.min_price,
            max_price: values.max_price,
            p: '0'
        });
    }

    const filterChange = (prop) => (event) => {
        setValues({...values, [prop]: event.target.value});
    }

    function addCard(product_id) {
        var test = "?uuid=" +localStorage.getItem("card_id")+ "&product_id=" +product_id
        console.log(test)
        dispatch(addCart(test))
    }

    return (
        <div>

            <Grid container spacing={3}>
                <Grid item xs={12}>
                    <h1 style={{margin: 0}}>Фильтр</h1>
                </Grid>
                <Grid container item xs={12}>
                    <Grid item xs={4}>
                        <h3 style={{margin: 0}}>Наименование</h3>
                    </Grid>
                    <Grid item xs={4}>
                        <h3 style={{margin: 0}}>Мин. цена</h3>
                    </Grid>
                    <Grid item xs={4}>
                        <h3 style={{margin: 0}}>Мак. цена</h3>
                    </Grid>
                </Grid>
                <Grid container item xs={12}>
                    <Grid item xs={4}>
                        <TextField id="title" onChange={filterChange('title')}/>
                    </Grid>
                    <Grid item xs={4}>
                        <TextField id="min_price" onChange={filterChange('min_price')}/>
                    </Grid>
                    <Grid item xs={4}>
                        <TextField id="max_price" onChange={filterChange('max_price')}/>
                    </Grid>
                </Grid>
                <Grid item xs={12}>
                    <Button variant="contained" color="primary" onClick={clickFilter}>
                        Применить
                    </Button>
                </Grid>
            </Grid>
            <Table>
                <TableHead>
                    <TableRow>
                        <TableCell>Название</TableCell>
                        <TableCell>Цена</TableCell>
                        <TableCell></TableCell>
                    </TableRow>
                </TableHead>
                <TableBody>
                    {products.map((product, indexRow) => (
                        <TableRow key={indexRow}>
                            <TableCell>{product.title}</TableCell>
                            <TableCell>{product.price}</TableCell>
                            <TableCell>
                                <Button variant="contained" color="primary" onClick={()=>addCard (product.id)}>
                                    Добавить в корзину
                                </Button>
                            </TableCell>
                        </TableRow>
                    ))}
                </TableBody>
            </Table>
            <Pagination count={pagination} color="primary" onChange={handleChange}/>
        </div>
    )
}

export default Product